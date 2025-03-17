describe('User information spec', () => {
    beforeEach(() => {
        cy.login(); // Utilise la commande personnalisée pour se connecter
    });

    it('Show user information as admin', () => {
        cy.intercept({
            method: 'GET',
            url: '/api/user/1',
        }, 
        {
            "id": 1,
            "email": "yoga@studio.com",
            "lastName": "Admin",
            "firstName": "Admin",
            "admin": true,
            "createdAt": "2025-03-08T22:53:08",
            "updatedAt": "2025-03-08T22:53:08"
        }).as('user')
    
        cy.get('span[routerlink=me]').click();
        cy.wait('@user');
        cy.contains('User information').should('be.visible');
        cy.url().should('include', '/me')

    })
})