import { Component, AfterViewInit, ViewChild, ElementRef } from '@angular/core';
import { MapService, PlacesService } from '../../services';
import mapboxgl, { Marker, Popup } from 'mapbox-gl';
import { CustomFeature, Feature, PlacesResponse } from '../../interfaces/places';
import { IconMap, Place } from '../../interfaces/places.map';


//  We need to load the CSS asset for MapboxGL otherwise it won't work.
@Component({
  selector: 'app-map-view',
  templateUrl: './map-view.component.html',
  styleUrl: './map-view.component.css'
})
export class MapViewComponent implements AfterViewInit {

  // Se declara  el elemento del DOM que va a contener al mapa
  @ViewChild('mapDiv')
    mapDivElement!: ElementRef

  //  Variables para trabajar con el mapa y sus características
  constructor(
    private placesService: PlacesService,
    private mapService: MapService
  ) { }

  //  Inicialización del componente después de cargado en la vista
  ngAfterViewInit(): void {

    // Declarar una variable para almacenar el marcador actual
    let currentMarker: mapboxgl.Marker | null = null;
    
    // 
    if (!this.placesService.userLocation) throw Error('No hay placesService.userLocation')
    

    // Se le dan las caracteristicas al mapa
    const map = new mapboxgl.Map({
	    container: this.mapDivElement.nativeElement,
	    style: 'mapbox://styles/iamjuaness/clv1c9h5h01dc01pedsbyaw81', // style URL
	    center: this.placesService.userLocation, // starting position [lng, lat]
      zoom: 16.6, // starting zoom
    });

  // Agregar el evento de clic al mapa
  map.on("click", function (e) {
      // Verificar si ya existe un marcador
      if (currentMarker) {
          // Si existe, eliminar el marcador actual
          currentMarker.remove();
      }

      // Crear un nuevo marcador en la posición del clic
      currentMarker = new mapboxgl.Marker({
          draggable: true
      }).setLngLat([e.lngLat.lng, e.lngLat.lat]).addTo(map);

      // Agregar un evento de "dragend" al marcador para obtener la nueva posición
      currentMarker.on("dragend", function () {
          var lngLat = currentMarker!.getLngLat();
          console.log(lngLat);
      });
  });



    // clase popup
    const popup = new Popup()
      .setHTML(`
        <h6>Aqui estoy</h6>
        <span>${this.placesService.userLocation}</span>
      `);
    
    // Definir un Marker
    new Marker({ color: 'gray' })
      .setLngLat(this.placesService.userLocation)
      .setPopup(popup)
      .addTo(map);
    
    this.mapService.setMap(map);

  // Ejemplo de solicitud HTTP utilizando fetch
  fetch('http://localhost:8080/api/auth/get-places')
      .then(response => response.json())
      .then((data: any) => { // Supongamos que los datos devueltos tienen un formato diferente al de las características
          // Verifica si los datos no son undefined
          if (data) {
              // Convierte los datos a características utilizando la función placeToFeature
            const features: CustomFeature[] = data.map(placeToFeature);
            console.log(features)
              features.forEach((feature: CustomFeature, index: number) => {
                
                this.mapService.lugares.push(feature)
                const place = data[index]; 

                  // Agrega la característica al mapa
                  map.addLayer({
                    id: feature.id,
                    type: 'symbol',
                    source: {
                        type: 'geojson',
                        data: {
                            type: 'FeatureCollection',
                            features: [feature]
                        }
                    },
                    layout: {
                        'icon-image': typePlace(place.businessType),
                        'text-field': feature.text, // Mostrará el nombre del lugar como texto
                        'text-font' : ['DIN Pro Bold'],
                        'text-size': 15, // Tamaño del texto
                        'text-anchor': 'top', // Anclaje del texto
                        'text-offset': [0, 1], // Desplazamiento del texto
                        'text-allow-overlap': true, // Permitir que se superponga el texto
                      },
                      paint: {
                        // Puedes personalizar la apariencia del icono aquí si es necesario
                        'text-color': '#9d9daf', // Color
                        'text-halo-color': '#ffffff', // Color del contorno
                        'text-halo-width': 1, // Ancho del contorno en píxeles 
                        // 'text-opacity': {
                        //     stops: [
                        //         [10, 1], // Visible a una distancia de 10 pixels
                        //         [15, 0]  // Invisible a una distancia de 15 pixels
                        //     ],
                        //   base: 2
                        // }
                      }
                  });

                
              });
          } else {
              console.error('La respuesta del servidor es incorrecta o está vacía.');
          }
      })
      .catch(error => console.error('Error al obtener los datos:', error));
    

  }
  


}

const iconMap: IconMap = {
  PANADERIA: 'bakery',
  CAFETERIA: 'cafe',
  BAR: 'bar',
  RESTAURANTE: 'restaurant',
  DISCOTECA: 'bar',
  SUPERMERCADO: 'grocery',
  TIENDA: 'shop',
  OTRO: 'border-dot-13'
};

function typePlace(type: string): string {
  const businessType = type

  return iconMap[businessType] || "default-icon";
}

// Define una función para convertir un Place en un Feature
function placeToFeature(place: Place): CustomFeature {
    return {
        id: "poi."+place.id,
        type: "Feature", // Asegúrate de que type sea "Feature"
        place_type: ['poi'], // Supongo que el tipo de lugar es "place" en este caso
        relevance: 1, // Puedes asignar un valor de relevancia según sea necesario
        properties: {
            mapbox_id: ""+place.id, // Utilizo el ID como mapbox_id
        },
        text_es: place.name, // Utilizo el nombre como texto en español
        place_name_es: place.description, // Utilizo el nombre como nombre de lugar en español
        text: place.name, // Utilizo el nombre como texto
        place_name: place.description, // Utilizo el nombre como nombre de lugar
        bbox: [place.location.lng, place.location.lat, place.location.lng, place.location.lat], // Establezco un bbox con las coordenadas del lugar
        center: [place.location.lng, place.location.lat], // Establezco el centro con las coordenadas del lugar
        geometry: {
            type: "Point",
            coordinates: [place.location.lng, place.location.lat] // Establezco las coordenadas del lugar
        }
    };
}
