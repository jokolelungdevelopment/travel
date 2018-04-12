import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('Request e2e test', () => {

    let navBarPage: NavBarPage;
    let requestDialogPage: RequestDialogPage;
    let requestComponentsPage: RequestComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Requests', () => {
        navBarPage.goToEntity('request');
        requestComponentsPage = new RequestComponentsPage();
        expect(requestComponentsPage.getTitle())
            .toMatch(/travelApp.request.home.title/);

    });

    it('should load create Request dialog', () => {
        requestComponentsPage.clickOnCreateButton();
        requestDialogPage = new RequestDialogPage();
        expect(requestDialogPage.getModalTitle())
            .toMatch(/travelApp.request.home.createOrEditLabel/);
        requestDialogPage.close();
    });

    it('should create and save Requests', () => {
        requestComponentsPage.clickOnCreateButton();
        requestDialogPage.setRequestDateInput(12310020012301);
        expect(requestDialogPage.getRequestDateInput()).toMatch('2001-12-31T02:30');
        requestDialogPage.setViewedInput('5');
        expect(requestDialogPage.getViewedInput()).toMatch('5');
        requestDialogPage.statusSelectLastOption();
        requestDialogPage.setTraveleridInput('5');
        expect(requestDialogPage.getTraveleridInput()).toMatch('5');
        requestDialogPage.userSelectLastOption();
        requestDialogPage.productSelectLastOption();
        requestDialogPage.save();
        expect(requestDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class RequestComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-request div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class RequestDialogPage {
    modalTitle = element(by.css('h4#myRequestLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    requestDateInput = element(by.css('input#field_requestDate'));
    viewedInput = element(by.css('input#field_viewed'));
    statusSelect = element(by.css('select#field_status'));
    traveleridInput = element(by.css('input#field_travelerid'));
    userSelect = element(by.css('select#field_user'));
    productSelect = element(by.css('select#field_product'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setRequestDateInput = function(requestDate) {
        this.requestDateInput.sendKeys(requestDate);
    };

    getRequestDateInput = function() {
        return this.requestDateInput.getAttribute('value');
    };

    setViewedInput = function(viewed) {
        this.viewedInput.sendKeys(viewed);
    };

    getViewedInput = function() {
        return this.viewedInput.getAttribute('value');
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
    setTraveleridInput = function(travelerid) {
        this.traveleridInput.sendKeys(travelerid);
    };

    getTraveleridInput = function() {
        return this.traveleridInput.getAttribute('value');
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
