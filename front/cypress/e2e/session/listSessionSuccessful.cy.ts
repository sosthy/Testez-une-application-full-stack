describe('sessions spec', () => {
    beforeEach(() => {
        cy.login(); // Utilise la commande personnalisée pour se connecter
    });

    it('list session successfull', () => {
        cy.get('button[routerlink=create]').should('exist')
        cy.contains('button', 'Detail').should('exist')
        cy.contains('button', 'Edit').should('exist')
    })
})