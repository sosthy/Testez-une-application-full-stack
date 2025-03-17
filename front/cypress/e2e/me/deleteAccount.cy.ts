describe('User information spec', () => {
    beforeEach(() => {
        cy.login(); // Utilise la commande personnalisée pour se connecter
    });

    it('Delete account', () => {
        cy.intercept({
            method: 'GET',
            url: '/api/user/1',
        }, 
        {
            "id": 1,
            "email": "yoga@studio.com",
            "lastName": "Admin",
            "firstName": "Admin",
            "admin": false,
            "createdAt": "2025-03-08T22:53:08",
            "updatedAt": "2025-03-08T22:53:08"
        }).as('user')

        cy.intercept('DELETE', '/api/user/1', {
            statusCode: 200,
            body: { message: 'Resource deleted successfully' }
        }).as('deleteResource')

        cy.get('span[routerlink=me]').click();
        cy.wait('@user')
        cy.contains('button', 'Detail').click()

    })
})