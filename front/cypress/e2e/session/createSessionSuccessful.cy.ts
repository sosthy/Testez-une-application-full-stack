describe('sessions spec', () => {
    beforeEach(() => {
        cy.login(); // Utilise la commande personnalisée pour se connecter
    });

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
})