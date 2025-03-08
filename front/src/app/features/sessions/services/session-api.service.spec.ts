import { HttpClient, HttpClientModule } from '@angular/common/http';
import { TestBed } from '@angular/core/testing';
import { expect } from '@jest/globals';

import { SessionApiService } from './session-api.service';
import { of } from 'rxjs';
import { Session } from '../interfaces/session.interface';

describe('SessionsService', () => {
  let service: SessionApiService;
  let httpMock: jest.Mocked<HttpClient>;

  afterEach(() => {
    jest.clearAllMocks()
  })

  beforeEach(() => {
    httpMock = {
      delete: jest.fn(),
      post: jest.fn(),
      put: jest.fn()
    } as unknown as jest.Mocked<HttpClient>

    TestBed.configureTestingModule({
      imports:[
        HttpClientModule
      ],
      providers: [{provide: HttpClient, useValue: httpMock}]
    });
    service = TestBed.inject(SessionApiService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('delete session', () => {
    httpMock.delete.mockReturnValue(of('Success'))
    service.delete('12').subscribe({
      next: (result) => {
        expect(result).toEqual('Success')
        expect(httpMock.delete).toHaveBeenCalledWith('api/session/12')
      }
    })   
  })

  it('unParticipate session', () => {
    httpMock.delete.mockReturnValue(of('Success'))
    service.unParticipate('12', '10').subscribe({
      next: (result) => {
        expect(result).toEqual('Success')
        expect(httpMock.delete).toHaveBeenCalledWith('api/session/12/participate/10')
      }
    })
  })

  it('create session', () => {
    const session: Session = {users: [], name: 'John', description: 'RAS', date: new Date(), teacher_id: 12}
    httpMock.post.mockReturnValue(of(session))
    service.create(session).subscribe({
      next: (result) => {
        expect(result).toEqual(session)
        expect(httpMock.delete).toHaveBeenCalledWith('api/session', session)
      }
    })   
  })

  it('participate session', () => {
    httpMock.post.mockReturnValue(of())
    service.participate('12', '10').subscribe({
      next: (result) => {
        expect(result).toEqual('Success')
        expect(httpMock.delete).toHaveBeenCalledWith('api/session/12/participate/11', null)
      }
    })
  })

  it('update session', () => {
    const session: Session = {users: [], name: 'John', description: 'RAS', date: new Date(), teacher_id: 12}
    httpMock.put.mockReturnValue(of(session))
    service.update('12', session).subscribe({
      next: (result) => {
        expect(result).toEqual(session)
        expect(httpMock.put).toHaveBeenCalledWith('api/session/12', session)
      }
    })
  })
});
