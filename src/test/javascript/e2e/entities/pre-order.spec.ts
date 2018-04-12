import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('PreOrder e2e test', () => {

    let navBarPage: NavBarPage;
    let preOrderDialogPage: PreOrderDialogPage;
    let preOrderComponentsPage: PreOrderComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load PreOrders', () => {
        navBarPage.goToEntity('pre-order');
        preOrderComponentsPage = new PreOrderComponentsPage();
        expect(preOrderComponentsPage.getTitle())
            .toMatch(/travelApp.preOrder.home.title/);

    });

    it('should load create PreOrder dialog', () => {
        preOrderComponentsPage.clickOnCreateButton();
        preOrderDialogPage = new PreOrderDialogPage();
        expect(preOrderDialogPage.getModalTitle())
            .toMatch(/travelApp.preOrder.home.createOrEditLabel/);
        preOrderDialogPage.close();
    });

    it('should create and save PreOrders', () => {
        preOrderComponentsPage.clickOnCreateButton();
        preOrderDialogPage.setOrderDateInput(12310020012301);
        expect(preOrderDialogPage.getOrderDateInput()).toMatch('2001-12-31T02:30');
        preOrderDialogPage.statusSelectLastOption();
        preOrderDialogPage.userSelectLastOption();
        preOrderDialogPage.productSelectLastOption();
        preOrderDialogPage.tripSelectLastOption();
        preOrderDialogPage.save();
        expect(preOrderDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class PreOrderComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-pre-order div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class PreOrderDialogPage {
    modalTitle = element(by.css('h4#myPreOrderLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    orderDateInput = element(by.css('input#field_orderDate'));
    statusSelect = element(by.css('select#field_status'));
    userSelect = element(by.css('select#field_user'));
    productSelect = element(by.css('select#field_product'));
    tripSelect = element(by.css('select#field_trip'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setOrderDateInput = function(orderDate) {
        this.orderDateInput.sendKeys(orderDate);
    };

    getOrderDateInput = function() {
        return this.orderDateInput.getAttribute('value');
    };

    setStatusSelect = function(status) {
        this.statusSelect.sendKeys(status);
    };

    getStatusSelect = function() {
        return this.statusSelect.element(by.css('option:checked')).getText();
    };

    statusSelectLastOption = function() {
        this.statusSelect.all(by.tagName('option')).last().click();
    };
    userSelectLastOption = function() {
        this.userSelect.all(by.tagName('option')).last().click();
    };

    userSelectOption = function(option) {
        this.userSelect.sendKeys(option);
    };

    getUserSelect = function() {
        return this.userSelect;
    };

    getUserSelectedOption = function() {
        return this.userSelect.element(by.css('option:checked')).getText();
    };

    productSelectLastOption = function() {
        this.productSelect.all(by.tagName('option')).last().click();
    };

    productSelectOption = function(option) {
        this.productSelect.sendKeys(option);
    };

    getProductSelect = function() {
        return this.productSelect;
    };

    getProductSelectedOption = function() {
        return this.productSelect.element(by.css('option:checked')).getText();
    };

    tripSelectLastOption = function() {
        this.tripSelect.all(by.tagName('option')).last().click();
    };

    tripSelectOption = function(option) {
        this.tripSelect.sendKeys(option);
    };

    getTripSelect = function() {
        return this.tripSelect;
    };

    getTripSelectedOption = function() {
        return this.tripSelect.element(by.css('option:checked')).getText();
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
