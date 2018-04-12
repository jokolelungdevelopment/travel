import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('Currency e2e test', () => {

    let navBarPage: NavBarPage;
    let currencyDialogPage: CurrencyDialogPage;
    let currencyComponentsPage: CurrencyComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Currencies', () => {
        navBarPage.goToEntity('currency');
        currencyComponentsPage = new CurrencyComponentsPage();
        expect(currencyComponentsPage.getTitle())
            .toMatch(/travelApp.currency.home.title/);

    });

    it('should load create Currency dialog', () => {
        currencyComponentsPage.clickOnCreateButton();
        currencyDialogPage = new CurrencyDialogPage();
        expect(currencyDialogPage.getModalTitle())
            .toMatch(/travelApp.currency.home.createOrEditLabel/);
        currencyDialogPage.close();
    });

    it('should create and save Currencies', () => {
        currencyComponentsPage.clickOnCreateButton();
        currencyDialogPage.setNameInput('name');
        expect(currencyDialogPage.getNameInput()).toMatch('name');
        currencyDialogPage.setValueInput('value');
        expect(currencyDialogPage.getValueInput()).toMatch('value');
        currencyDialogPage.save();
        expect(currencyDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class CurrencyComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-currency div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class CurrencyDialogPage {
    modalTitle = element(by.css('h4#myCurrencyLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    nameInput = element(by.css('input#field_name'));
    valueInput = element(by.css('input#field_value'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setNameInput = function(name) {
        this.nameInput.sendKeys(name);
    };

    getNameInput = function() {
        return this.nameInput.getAttribute('value');
    };

    setValueInput = function(value) {
        this.valueInput.sendKeys(value);
    };

    getValueInput = function() {
        return this.valueInput.getAttribute('value');
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
