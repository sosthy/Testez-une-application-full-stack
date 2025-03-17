describe('Logout spec', () => {
    beforeEach(() => {
        cy.login(); // Utilise la commande personnalisée pour se connecter
    });
    
    it('Logout successfull', () => {
      cy.contains('span', 'Logout').click();
    })

  });