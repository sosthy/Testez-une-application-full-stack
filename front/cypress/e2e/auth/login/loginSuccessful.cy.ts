describe('Login spec', () => {
  beforeEach(() => {
    cy.visit('/login'); // Visite avant chaque test pour éviter la répétition
  });

  it('Login successful', () => {
    cy.intercept('POST', '/api/auth/login', {
      statusCode: 200,
      body: {
        id: 1,
        username: 'userName',
        firstName: 'firstName',
        lastName: 'lastName',
        admin: true
      },
      headers: { 'content-type': 'application/json' }
    }).as('loginRequest');

    cy.intercept('GET', '/api/session', []).as('session');

    cy.get('input[formControlName=email]').type("yoga@studio.com");
    cy.get('input[formControlName=password]').type("test!1234");
    cy.get('button[type=submit]').click();

    cy.wait('@loginRequest'); // Attendre la requête login
    cy.url({ timeout: 5000 }).should('include', '/sessions'); // Vérifier la redirection
  });
});
