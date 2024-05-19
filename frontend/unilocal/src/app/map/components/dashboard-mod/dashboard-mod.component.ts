import { Component, OnInit } from '@angular/core';
import { Chart, registerables } from 'chart.js';
import { AuthService } from '../../services/auth-service.service';
import { Lugar } from '../../class/model/lugar';
import { CommonModule } from '@angular/common';

Chart.register(...registerables);

@Component({
  standalone: true,
  imports: [CommonModule],
  selector: 'app-dashboard',
  templateUrl: './dashboard-mod.component.html',
  styleUrls: ['./dashboard-mod.component.css']
})
export class DashboardModComponent implements OnInit {

  lugaresEnRevision!: Lugar[];

  constructor(private authService: AuthService) {}

  ngOnInit(): void {
    this.initUserChart();
    this.initCommerceChart();
    this.obtenerLugaresEnRevision();
  }

initUserChart() {
  // Obtener el canvas del gráfico de usuarios
  const ctx = document.getElementById('usersChart') as HTMLCanvasElement;

  // Llamar al método para obtener todos los usuarios
  this.authService.obtenerUsuarios().then(response => {
    // Extraer la lista de usuarios del objeto de respuesta
    const users = response.data;

    // Contar la cantidad de usuarios registrados
    const numRegisteredUsers = users.length;

    // Crear el gráfico de usuarios con la cantidad de usuarios registrados
    new Chart(ctx, {
      type: 'doughnut',
      data: {
        labels: ['Usuarios Registrados'],
        datasets: [{
          data: [numRegisteredUsers],
          backgroundColor: ['yellow'], // Cambiar el color del gráfico si lo deseas
        }],
      },
      options: {
        responsive: true,
      },
    });
  }).catch((error) => {
    // Manejar errores de solicitud
    console.error('Error al obtener usuarios:', error);
  });
}

initCommerceChart() {
  // Obtener el canvas del gráfico de comercios
  const ctx2 = document.getElementById('commercesChart') as HTMLCanvasElement;

  // Llamar al método para obtener todos los lugares
  this.authService.obtenerLugares().then(response => {
    // Extraer la lista de lugares del objeto de respuesta
    const places = response.data;

    // Inicializar contadores para cada estado de negocio
    let activos = 0;
    let enRevision = 0;
    let rechazados = 0;
    let inactivos = 0;

    // Iterar sobre cada lugar y contar según su estado de negocio
    places.forEach((place: Lugar) => {
      switch (place.stateBusiness) {
        case 'Active':
          activos++;
          break;
        case 'Revision':
          enRevision++;
          break;
        case 'Refused':
          rechazados++;
          break;
        case 'Inactive':
          inactivos++;
          break;
        default:
          break;
      }
    });
    // Crear el gráfico de comercios con la cantidad de lugares en cada estado
    new Chart(ctx2, {
      type: 'doughnut',
      data: {
        labels: ['Activos', 'En Revisión'],
        datasets: [{
          data: [activos, enRevision],
          backgroundColor: ['cyan', 'yellow'], // Cambiar los colores del gráfico si lo deseas
        }],
      },
      options: {
        responsive: true,
      },
    });
  }).catch(error => {
    // Manejar errores de solicitud
    console.error('Error al obtener lugares:', error);
  });
}

  highlightSidebarItem(event: Event) {
    const element = event.currentTarget as HTMLElement;
    const buttons = document.querySelectorAll('#sidebar button');
    buttons.forEach(btn => {
      btn.classList.remove('bg-gradient-to-r', 'from-cyan-400', 'to-cyan-500', 'text-white', 'w-48', 'ml-0');
      const span = btn.querySelector('span');
      if (span) {
        span.classList.remove('text-white');
      }
    });
    element.classList.add('bg-gradient-to-r', 'from-cyan-400', 'to-cyan-500', 'w-56', 'h-10', 'ml-0');
    const span = element.querySelector('span');
    if (span) {
      span.classList.add('text-white');
    }
  }

  // Método para obtener los lugares en revisión
  async obtenerLugaresEnRevision() {
    try {
      const response = await this.authService.obtenerLugares();
      // Filtrar los lugares en revisión
      this.lugaresEnRevision = response.data.filter((lugar: Lugar) => lugar.stateBusiness === 'Revision');
    } catch (error) {
      console.error('Error al obtener lugares en revisión:', error);
      throw error; // Propagar el error para que se maneje en la capa superior
    }
  }
  
}
