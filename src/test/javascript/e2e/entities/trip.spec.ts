import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('Trip e2e test', () => {

    let navBarPage: NavBarPage;
    let tripDialogPage: TripDialogPage;
    let tripComponentsPage: TripComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Trips', () => {
        navBarPage.goToEntity('trip');
        tripComponentsPage = new TripComponentsPage();
        expect(tripComponentsPage.getTitle())
            .toMatch(/travelApp.trip.home.title/);

    });

    it('should load create Trip dialog', () => {
        tripComponentsPage.clickOnCreateButton();
        tripDialogPage = new TripDialogPage();
        expect(tripDialogPage.getModalTitle())
            .toMatch(/travelApp.trip.home.createOrEditLabel/);
        tripDialogPage.close();
    });

    it('should create and save Trips', () => {
        tripComponentsPage.clickOnCreateButton();
        tripDialogPage.setStartDateInput(12310020012301);
        expect(tripDialogPage.getStartDateInput()).toMatch('2001-12-31T02:30');
        tripDialogPage.setEndDateInput(12310020012301);
        expect(tripDialogPage.getEndDateInput()).toMatch('2001-12-31T02:30');
        tripDialogPage.userSelectLastOption();
        tripDialogPage.save();
        expect(tripDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class TripComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-trip div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class TripDialogPage {
    modalTitle = element(by.css('h4#myTripLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    startDateInput = element(by.css('input#field_startDate'));
    endDateInput = element(by.css('input#field_endDate'));
    userSelect = element(by.css('select#field_user'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setStartDateInput = function(startDate) {
        this.startDateInput.sendKeys(startDate);
    };

    getStartDateInput = function() {
        return this.startDateInput.getAttribute('value');
    };

    setEndDateInput = function(endDate) {
        this.endDateInput.sendKeys(endDate);
    };

    getEndDateInput = function() {
        return this.endDateInput.getAttribute('value');
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
