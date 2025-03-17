describe('sessions spec', () => {
    beforeEach(() => {
        cy.login(); // Utilise la commande personnalisée pour se connecter
    });

    it('detail session successfull', () => {
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