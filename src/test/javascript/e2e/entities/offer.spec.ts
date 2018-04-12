import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('Offer e2e test', () => {

    let navBarPage: NavBarPage;
    let offerDialogPage: OfferDialogPage;
    let offerComponentsPage: OfferComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Offers', () => {
        navBarPage.goToEntity('offer');
        offerComponentsPage = new OfferComponentsPage();
        expect(offerComponentsPage.getTitle())
            .toMatch(/travelApp.offer.home.title/);

    });

    it('should load create Offer dialog', () => {
        offerComponentsPage.clickOnCreateButton();
        offerDialogPage = new OfferDialogPage();
        expect(offerDialogPage.getModalTitle())
            .toMatch(/travelApp.offer.home.createOrEditLabel/);
        offerDialogPage.close();
    });

    it('should create and save Offers', () => {
        offerComponentsPage.clickOnCreateButton();
        offerDialogPage.setOfferDateInput(12310020012301);
        expect(offerDialogPage.getOfferDateInput()).toMatch('2001-12-31T02:30');
        offerDialogPage.setPriceInput('5');
        expect(offerDialogPage.getPriceInput()).toMatch('5');
        offerDialogPage.setNoteInput('note');
        expect(offerDialogPage.getNoteInput()).toMatch('note');
        offerDialogPage.statusSelectLastOption();
        offerDialogPage.userSelectLastOption();
        offerDialogPage.requestSelectLastOption();
        offerDialogPage.tripSelectLastOption();
        offerDialogPage.save();
        expect(offerDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class OfferComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-offer div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class OfferDialogPage {
    modalTitle = element(by.css('h4#myOfferLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    offerDateInput = element(by.css('input#field_offerDate'));
    priceInput = element(by.css('input#field_price'));
    noteInput = element(by.css('input#field_note'));
    statusSelect = element(by.css('select#field_status'));
    userSelect = element(by.css('select#field_user'));
    requestSelect = element(by.css('select#field_request'));
    tripSelect = element(by.css('select#field_trip'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setOfferDateInput = function(offerDate) {
        this.offerDateInput.sendKeys(offerDate);
    };

    getOfferDateInput = function() {
        return this.offerDateInput.getAttribute('value');
    };

    setPriceInput = function(price) {
        this.priceInput.sendKeys(price);
    };

    getPriceInput = function() {
        return this.priceInput.getAttribute('value');
    };

    setNoteInput = function(note) {
        this.noteInput.sendKeys(note);
    };

    getNoteInput = function() {
        return this.noteInput.getAttribute('value');
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

    requestSelectLastOption = function() {
        this.requestSelect.all(by.tagName('option')).last().click();
    };

    requestSelectOption = function(option) {
        this.requestSelect.sendKeys(option);
    };

    getRequestSelect = function() {
        return this.requestSelect;
    };

    getRequestSelectedOption = function() {
        return this.requestSelect.element(by.css('option:checked')).getText();
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
