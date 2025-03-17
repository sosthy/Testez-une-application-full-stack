describe('sessions spec', () => {
    beforeEach(() => {
        cy.login(); // Utilise la commande personnalisée pour se connecter
    });

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
            }, 
            {
                "id": 1,
                "name": "DIDIER Cyril",
                "date": "2025-03-14T00:00:00.000+00:00",
                "teacher_id": 1,
                "description": "Description 001",
                "users": [],
                "createdAt": "2025-03-08T23:23:10",
                "updatedAt": "2025-03-08T23:23:10"
            }).as('session')

        cy.get('button').contains('edit').click()
        cy.url().should('contains', '/sessions/update/1')

        cy.get('input[formControlName=name]').clear().type("DIDIER Cyril")
        cy.get('button[type=submit]').click()

        cy.url().should('contains', '/sessions')
        cy.get('button[routerlink=create]').should('exist')
    })
})