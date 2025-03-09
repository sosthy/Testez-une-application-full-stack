describe('sessions spec', () => {

    it('list session successfull', () => {
        cy.visit('/login')

        cy.intercept('POST', '/api/auth/login', {
            body: {
                id: 1,
                username: 'userName',
                firstName: 'firstName',
                lastName: 'lastName',
                admin: true
            },
        })
      
        cy.intercept(
        {
            method: 'GET',
            url: '/api/session',
        }, 
        [
            {
                "id": 1,
                "name": "Session 1",
                "date": "2025-03-14T00:00:00.000+00:00",
                "teacher_id": 1,
                "description": "RAS 1",
                "users": [],
                "createdAt": "2025-03-08T23:23:10",
                "updatedAt": "2025-03-08T23:23:10"
            }
        ]).as('session')

        cy.get('input[formControlName=email]').type("yoga@studio.com")
        cy.get('input[formControlName=password]').type(`${"test!1234"}{enter}{enter}`)

        cy.url().should('include', '/sessions')
        cy.get('button[routerlink=create]').should('exist')
        cy.contains('button', 'Detail').should('exist')
        cy.contains('button', 'Edit').should('exist')
    })

    it('create session successfull', () => {
      
        cy.intercept(
        {
            method: 'GET',
            url: '/api/teacher',
        }, 
        [
            {
                "id": 1,
                "lastName": "DELAHAYE",
                "firstName": "Margot",
                "createdAt": "2025-03-08T22:53:08",
                "updatedAt": "2025-03-08T22:53:08"
            },
            {
                "id": 2,
                "lastName": "THIERCELIN",
                "firstName": "Hélène",
                "createdAt": "2025-03-08T22:53:08",
                "updatedAt": "2025-03-08T22:53:08"
            }
        ]).as('teacher')

        cy.intercept('POST', '/api/session', {
            body: {
                "name": "John Doe",
                "date": "2025-03-14",
                "teacher_id": 1,
                "description": "Description 001"
            },
        })

        cy.intercept(
            {
                method: 'GET',
                url: '/api/session',
            }, 
            [
                {
                    "id": 1,
                    "name": "John Doe",
                    "date": "2025-03-14T00:00:00.000+00:00",
                    "teacher_id": 1,
                    "description": "Description 001",
                    "users": [],
                    "createdAt": "2025-03-08T23:23:10",
                    "updatedAt": "2025-03-08T23:23:10"
                }
            ]).as('session')

        cy.get('button[routerlink=create]').click()

        cy.get('input[formControlName=name]').type("John Doe")
        cy.get('input[formControlName=date]').type("2025-03-08")
        cy.get('mat-select').click()
        cy.get('span').contains('Hélène THIERCELIN').click();
        cy.get('textarea[formControlName=description]').type(`${"description..."}`)
        cy.get('button[type=submit]').click()

        cy.url().should('contains', '/sessions')
        cy.get('button[routerlink=create]').should('exist')
        cy.contains('button', 'Detail').should('exist')
        cy.contains('button', 'Edit').should('exist')
    })

    it('edit session successfull', () => {
      
        cy.intercept(
        {
            method: 'GET',
            url: '/api/teacher',
        }, 
        [
            {
                "id": 1,
                "lastName": "DELAHAYE",
                "firstName": "Margot",
                "createdAt": "2025-03-08T22:53:08",
                "updatedAt": "2025-03-08T22:53:08"
            },
            {
                "id": 2,
                "lastName": "THIERCELIN",
                "firstName": "Hélène",
                "createdAt": "2025-03-08T22:53:08",
                "updatedAt": "2025-03-08T22:53:08"
            }
        ]).as('teacher')

        cy.intercept('POST', '/api/session', {
            body: {
                "id": 1,
                "name": "John Doe",
                "date": "2025-03-14",
                "teacher_id": 1,
                "description": "Description 001"
            },
        })

        cy.intercept('PUT', '/api/session/1', {
            body: {
                "id": 1,
                "name": "DIDIER Cyril",
                "date": "2025-03-14",
                "teacher_id": 1,
                "description": "Description 001"
            },
        })

        cy.intercept(
            {
                method: 'GET',
                url: '/api/session/1',
            }, {
                    "id": 1,
                    "name": "John Doe",
                    "date": "2025-03-14T00:00:00.000+00:00",
                    "teacher_id": 1,
                    "description": "Description 001",
                    "users": [],
                    "createdAt": "2025-03-08T23:23:10",
                    "updatedAt": "2025-03-08T23:23:10"
                }
            ).as('session')
        
            cy.intercept(
                {
                    method: 'GET',
                    url: '/api/session',
                }, 
                [
                    {
                        "id": 1,
                        "name": "DIDIER Cyril",
                        "date": "2025-03-14T00:00:00.000+00:00",
                        "teacher_id": 1,
                        "description": "Description 001",
                        "users": [],
                        "createdAt": "2025-03-08T23:23:10",
                        "updatedAt": "2025-03-08T23:23:10"
                    }
                ]).as('session')

        cy.get('button').contains('edit').click()
        cy.url().should('contains', '/sessions/update/1')

        cy.get('input[formControlName=name]').clear().type("DIDIER Cyril")
        cy.get('button[type=submit]').click()

        cy.url().should('contains', '/sessions')
        cy.get('button[routerlink=create]').should('exist')
    })

    it('detail session successfull', () => {

        cy.intercept(
            {
                method: 'GET',
                url: '/api/session/1',
            }, {
                    "id": 1,
                    "name": "DIDIER Cyril",
                    "date": "2025-03-14T00:00:00.000+00:00",
                    "teacher_id": 1,
                    "description": "Description 001",
                    "users": [],
                    "createdAt": "2025-03-08T23:23:10",
                    "updatedAt": "2025-03-08T23:23:10"
                }
            ).as('session')
        
            cy.intercept(
                {
                    method: 'GET',
                    url: '/api/teacher/1',
                }, {
                        "id": 1,
                        "lastName": "DELAHAYE",
                        "firstName": "Margot",
                        "createdAt": "2025-03-08T22:53:08",
                        "updatedAt": "2025-03-08T22:53:08"
                    },
                ).as('teacher')

        cy.get('button').contains('Detail').click()
        cy.url().should('contains', '/sessions/detail/1')
        cy.get('button').contains('Delete')
    })
})