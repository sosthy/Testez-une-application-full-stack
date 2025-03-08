import { TestBed } from '@angular/core/testing';
import { expect } from '@jest/globals';

import { SessionService } from './session.service';
import { SessionInformation } from '../interfaces/sessionInformation.interface';

describe('SessionService', () => {
  let service: SessionService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SessionService);
  });

  it('logIn feature', () => {
    const sessionInformation: SessionInformation = {
      token: '',
      type: '',
      id: 12,
      username: 'test',
      firstName: 'john',
      lastName: 'doe',
      admin: false
    }
    service.logIn(sessionInformation);
    expect(service.isLogged).toEqual(true)
    expect(service.sessionInformation).toEqual(sessionInformation)
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('logOut feature', () => {
    service.logOut()
    expect(service.isLogged).toEqual(false)
    expect(service.sessionInformation).not.toBeDefined()
  });

  it('$isLogged feature', () => {
    service.isLogged = true
    service.$isLogged().subscribe({
      next: (result) => expect(result).toEqual(true)
    })
  });
});
