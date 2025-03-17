describe('register spec', () => {
    
    beforeEach(() => {
        cy.visit('/register');
    });

    it('register successfull', () => {
        cy.intercept('POST', '/api/auth/register', {
            statusCode: 200,
            body: { message: "User registered successfully!" },
            headers: { 'content-type': 'application/json' }
        }).as('registerRequest');

        cy.get('input[formControlName=firstName]').type("Sosthene");
        cy.get('input[formControlName=lastName]').type("NOUEBISSI NGHEMNIN");
        cy.get('input[formControlName=email]').type("yoga@studio.com");
        cy.get('input[formControlName=password]').type("test!1234");
        cy.get('button[type=submit]').click();

        cy.wait('@registerRequest');
        cy.url({ timeout: 5000 }).should('include', '/login');
    });
});
