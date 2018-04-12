import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('Transaction e2e test', () => {

    let navBarPage: NavBarPage;
    let transactionDialogPage: TransactionDialogPage;
    let transactionComponentsPage: TransactionComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Transactions', () => {
        navBarPage.goToEntity('transaction');
        transactionComponentsPage = new TransactionComponentsPage();
        expect(transactionComponentsPage.getTitle())
            .toMatch(/travelApp.transaction.home.title/);

    });

    it('should load create Transaction dialog', () => {
        transactionComponentsPage.clickOnCreateButton();
        transactionDialogPage = new TransactionDialogPage();
        expect(transactionDialogPage.getModalTitle())
            .toMatch(/travelApp.transaction.home.createOrEditLabel/);
        transactionDialogPage.close();
    });

    it('should create and save Transactions', () => {
        transactionComponentsPage.clickOnCreateButton();
        transactionDialogPage.setRequestidInput('5');
        expect(transactionDialogPage.getRequestidInput()).toMatch('5');
        transactionDialogPage.setPreorderidInput('5');
        expect(transactionDialogPage.getPreorderidInput()).toMatch('5');
        transactionDialogPage.setDeliverytoInput('deliveryto');
        expect(transactionDialogPage.getDeliverytoInput()).toMatch('deliveryto');
        transactionDialogPage.setQtyInput('5');
        expect(transactionDialogPage.getQtyInput()).toMatch('5');
        transactionDialogPage.setPriceInput('5');
        expect(transactionDialogPage.getPriceInput()).toMatch('5');
        transactionDialogPage.currencySelectLastOption();
        transactionDialogPage.save();
        expect(transactionDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class TransactionComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-transaction div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class TransactionDialogPage {
    modalTitle = element(by.css('h4#myTransactionLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    requestidInput = element(by.css('input#field_requestid'));
    preorderidInput = element(by.css('input#field_preorderid'));
    deliverytoInput = element(by.css('textarea#field_deliveryto'));
    qtyInput = element(by.css('input#field_qty'));
    priceInput = element(by.css('input#field_price'));
    currencySelect = element(by.css('select#field_currency'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setRequestidInput = function(requestid) {
        this.requestidInput.sendKeys(requestid);
    };

    getRequestidInput = function() {
        return this.requestidInput.getAttribute('value');
    };

    setPreorderidInput = function(preorderid) {
        this.preorderidInput.sendKeys(preorderid);
    };

    getPreorderidInput = function() {
        return this.preorderidInput.getAttribute('value');
    };

    setDeliverytoInput = function(deliveryto) {
        this.deliverytoInput.sendKeys(deliveryto);
    };

    getDeliverytoInput = function() {
        return this.deliverytoInput.getAttribute('value');
    };

    setQtyInput = function(qty) {
        this.qtyInput.sendKeys(qty);
    };

    getQtyInput = function() {
        return this.qtyInput.getAttribute('value');
    };

    setPriceInput = function(price) {
        this.priceInput.sendKeys(price);
    };

    getPriceInput = function() {
        return this.priceInput.getAttribute('value');
    };

    currencySelectLastOption = function() {
        this.currencySelect.all(by.tagName('option')).last().click();
    };

    currencySelectOption = function(option) {
        this.currencySelect.sendKeys(option);
    };

    getCurrencySelect = function() {
        return this.currencySelect;
    };

    getCurrencySelectedOption = function() {
        return this.currencySelect.element(by.css('option:checked')).getText();
    };

    save() {
        this.saveButton.click();
    }

    close() {
        this.closeButton.click();
    }

    getSaveButton() {
        return this.saveButton;
    }
}
