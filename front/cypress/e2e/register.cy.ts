describe('register spec', () => {
    it('register successfull', () => {
        cy.visit('/register')

        cy.intercept('POST', '/api/auth/register', {"message":"User registered successfully!"})

        cy.get('input[formControlName=firstName]').type("Sosthene")
        cy.get('input[formControlName=lastName]').type("NOUEBISSI NGHEMNIN")
        cy.get('input[formControlName=email]').type("yoga@studio.com")
        cy.get('input[formControlName=password]').type(`${"test!1234"}{enter}{enter}`)

        cy.url().should('include', '/login')
    })
})