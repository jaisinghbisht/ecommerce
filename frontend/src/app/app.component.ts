import { Component, OnInit } from '@angular/core';
import { AuthService } from './features/auth/services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
  title = 'ecommerce-frontend';

  constructor(
    public authService: AuthService, // Made public to be accessible in template
    private router: Router
  ) {}

  ngOnInit(): void {
    // No direct subscriptions needed here anymore, as template uses async pipe
  }

  logout(): void {
    this.authService.logout();
  }
}
