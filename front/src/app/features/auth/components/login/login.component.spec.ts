import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { RouterTestingModule } from '@angular/router/testing';
import { expect } from '@jest/globals';
import { SessionService } from 'src/app/services/session.service';

import { LoginComponent } from './login.component';
import { AuthService } from '../../services/auth.service';
import { LoginRequest } from '../../interfaces/loginRequest.interface';
import { of, throwError } from 'rxjs';
import { SessionInformation } from 'src/app/interfaces/sessionInformation.interface';
import { Router } from '@angular/router';

describe('LoginComponent', () => {
  let component: LoginComponent;
  let fixture: ComponentFixture<LoginComponent>;
  let authServiceMock: jest.Mocked<AuthService>;
  let routerMock: Partial<Router>;

  beforeEach(async () => {
    authServiceMock = {
      login: jest.fn()
    } as unknown as jest.Mocked<AuthService>
    routerMock = {
      navigate: jest.fn()
    };

    await TestBed.configureTestingModule({
      declarations: [LoginComponent],
      providers: [SessionService, {provide: AuthService, useValue: authServiceMock}, { provide: Router, useValue: routerMock }],
      imports: [
        RouterTestingModule,
        BrowserAnimationsModule,
        HttpClientModule,
        MatCardModule,
        MatIconModule,
        MatFormFieldModule,
        MatInputModule,
        ReactiveFormsModule]
    })
      .compileComponents();
    fixture = TestBed.createComponent(LoginComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it ('login is correct', () => {
    const sessionInformation: SessionInformation = {
      token: '',
      type: '',
      id: 12,
      username: 'test',
      firstName: 'john',
      lastName: 'doe',
      admin: false
    }
    authServiceMock.login.mockReturnValue(of(sessionInformation));
    const loginRequest: LoginRequest = {email: 'yoga@studio.com', password: 'test!1234'};
    component.form.setValue(loginRequest);
    component.submit();
    expect(component.onError).toEqual(false);
    expect(authServiceMock.login).toHaveBeenCalledWith(loginRequest);
    expect(routerMock.navigate).toHaveBeenCalledWith(['/sessions']);
  })

  it ('print error when login is not correct', () => {
    authServiceMock.login.mockReturnValue(throwError(() => new Error('Identifiant invalides')));
    const loginRequest: LoginRequest = {email: 'yoga@studio.com', password: 'test!1234'};
    component.form.setValue(loginRequest);
    component.submit();
    expect(component.onError).toEqual(true);
    expect(authServiceMock.login).toHaveBeenCalledWith(loginRequest);
  })
});
