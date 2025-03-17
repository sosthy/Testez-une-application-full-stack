describe('Login spec', () => {
    beforeEach(() => {
      cy.visit('/login'); // Visite avant chaque test pour éviter la répétition
    });
  
    it('Login email error', () => {
      cy.get('input[formControlName=email]').type("yogastudio.com");
      cy.get('input[formControlName=password]').type("test!1234");
  
      cy.get('input[formControlName=email]').should('have.attr', 'aria-invalid', 'true');
      cy.get('button[type=submit]').should('have.attr', 'disabled', 'disabled');
    });
})  