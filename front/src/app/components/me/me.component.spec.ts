import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { SessionService } from 'src/app/services/session.service';

import { MeComponent } from './me.component';
import { UserService } from 'src/app/services/user.service';
import { of } from 'rxjs';
import { Router } from '@angular/router';
import { User } from 'src/app/interfaces/user.interface';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { RouterTestingModule } from '@angular/router/testing';

describe('MeComponent', () => {
  let component: MeComponent;
  let fixture: ComponentFixture<MeComponent>;
  let mockSessionService: Partial<SessionService>;
  let mockUserService: Partial<UserService>;
  let routerMock: Partial<Router>;
  let matSnackBarMock: Partial<MatSnackBar>;


  const mockUser: User = { id: 1, firstName: 'John', lastName: 'Doe', password: '', admin: true, email: 'test@test.com', createdAt: new Date() };

  beforeEach(async () => {
    routerMock = {
      navigate: jest.fn(),
    }

    matSnackBarMock = {
      open: jest.fn(),
    };

    mockSessionService = {
      sessionInformation: { id: 1, admin: true, username: 'test', firstName: 'John', lastName: 'Doe', type: 'Auth', token: '' },
      logOut: jest.fn()
    }

    mockUserService = {
      getById: jest.fn().mockReturnValue(of(mockUser)),
      delete: jest.fn().mockReturnValue(of(null))
    } as unknown as jest.Mocked<UserService>

    await TestBed.configureTestingModule({
      declarations: [MeComponent],
      imports: [
        MatSnackBarModule,
        HttpClientModule,
        MatCardModule,
        MatFormFieldModule,
        MatIconModule,
        MatInputModule,
        RouterTestingModule,
                BrowserAnimationsModule,
      ],
      providers: [
        { provide: Router, useValue: routerMock },
        { provide: MatSnackBar, useValue: matSnackBarMock },
        { provide: SessionService, useValue: mockSessionService }, 
        { provide: UserService, useValue: mockUserService }],
    })
      .compileComponents();

    fixture = TestBed.createComponent(MeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  afterEach(() => {
    jest.clearAllMocks();
  });

  it('should load the user on init', () => {
    fixture.detectChanges();
    expect(mockUserService.getById).toHaveBeenCalledWith('1');
    expect(component.user).toEqual(mockUser);
  });

  it('ngOnInit component', () => {
    component.delete()
    expect(mockSessionService.logOut).toHaveBeenCalled()
    expect(matSnackBarMock.open).toHaveBeenCalled()
    expect(routerMock.navigate).toHaveBeenCalledWith(['/'])
  });
});
