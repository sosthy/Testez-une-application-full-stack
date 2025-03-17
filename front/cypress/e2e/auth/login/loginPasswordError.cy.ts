describe('Login spec', () => {
    beforeEach(() => {
      cy.visit('/login'); // Visite avant chaque test pour éviter la répétition
    });
  
    it('Login password error', () => {
      cy.intercept('POST', '/api/auth/login', {
        statusCode: 401,
        body: { error: 'Unauthorized' },
        headers: { 'content-type': 'application/json' }
      }).as('loginRequest');
  
      cy.get('input[formControlName=email]').type("yoga@studio.com");
      cy.get('input[formControlName=password]').type("test!1234");
      cy.get('button[type=submit]').click();
  
      cy.wait('@loginRequest');
      cy.contains('An error occurred', { timeout: 5000 }).should('be.visible');
    });
})