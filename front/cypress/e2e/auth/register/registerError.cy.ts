describe('register spec', () => {
    
    beforeEach(() => {
        cy.visit('/register');
    });

    it('register error', () => {
        cy.intercept('POST', '/api/auth/register', {
            statusCode: 401,
            body: { error: 'Unauthorized' },
            headers: { 'content-type': 'application/json' }
        }).as('registerRequest');

        cy.get('input[formControlName=firstName]').type("Sosthene");
        cy.get('input[formControlName=lastName]').type("NOUEBISSI NGHEMNIN");
        cy.get('input[formControlName=email]').type("yoga@studio.com");
        cy.get('input[formControlName=password]').type("test!1234");
        cy.get('button[type=submit]').click();

        cy.wait('@registerRequest');
        cy.contains('An error occurred', { timeout: 5000 }).should('be.visible');
    });
})