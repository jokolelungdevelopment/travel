import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('Journey e2e test', () => {

    let navBarPage: NavBarPage;
    let journeyDialogPage: JourneyDialogPage;
    let journeyComponentsPage: JourneyComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Journeys', () => {
        navBarPage.goToEntity('journey');
        journeyComponentsPage = new JourneyComponentsPage();
        expect(journeyComponentsPage.getTitle())
            .toMatch(/travelApp.journey.home.title/);

    });

    it('should load create Journey dialog', () => {
        journeyComponentsPage.clickOnCreateButton();
        journeyDialogPage = new JourneyDialogPage();
        expect(journeyDialogPage.getModalTitle())
            .toMatch(/travelApp.journey.home.createOrEditLabel/);
        journeyDialogPage.close();
    });

    it('should create and save Journeys', () => {
        journeyComponentsPage.clickOnCreateButton();
        journeyDialogPage.tripSelectLastOption();
        journeyDialogPage.citySelectLastOption();
        journeyDialogPage.countrySelectLastOption();
        journeyDialogPage.save();
        expect(journeyDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class JourneyComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-journey div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class JourneyDialogPage {
    modalTitle = element(by.css('h4#myJourneyLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    tripSelect = element(by.css('select#field_trip'));
    citySelect = element(by.css('select#field_city'));
    countrySelect = element(by.css('select#field_country'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

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

    citySelectLastOption = function() {
        this.citySelect.all(by.tagName('option')).last().click();
    };

    citySelectOption = function(option) {
        this.citySelect.sendKeys(option);
    };

    getCitySelect = function() {
        return this.citySelect;
    };

    getCitySelectedOption = function() {
        return this.citySelect.element(by.css('option:checked')).getText();
    };

    countrySelectLastOption = function() {
        this.countrySelect.all(by.tagName('option')).last().click();
    };

    countrySelectOption = function(option) {
        this.countrySelect.sendKeys(option);
    };

    getCountrySelect = function() {
        return this.countrySelect;
    };

    getCountrySelectedOption = function() {
        return this.countrySelect.element(by.css('option:checked')).getText();
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
