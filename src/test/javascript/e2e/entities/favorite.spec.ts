import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('Favorite e2e test', () => {

    let navBarPage: NavBarPage;
    let favoriteDialogPage: FavoriteDialogPage;
    let favoriteComponentsPage: FavoriteComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Favorites', () => {
        navBarPage.goToEntity('favorite');
        favoriteComponentsPage = new FavoriteComponentsPage();
        expect(favoriteComponentsPage.getTitle())
            .toMatch(/travelApp.favorite.home.title/);

    });

    it('should load create Favorite dialog', () => {
        favoriteComponentsPage.clickOnCreateButton();
        favoriteDialogPage = new FavoriteDialogPage();
        expect(favoriteDialogPage.getModalTitle())
            .toMatch(/travelApp.favorite.home.createOrEditLabel/);
        favoriteDialogPage.close();
    });

    it('should create and save Favorites', () => {
        favoriteComponentsPage.clickOnCreateButton();
        favoriteDialogPage.setRequestidInput('5');
        expect(favoriteDialogPage.getRequestidInput()).toMatch('5');
        favoriteDialogPage.setPreorderidInput('5');
        expect(favoriteDialogPage.getPreorderidInput()).toMatch('5');
        favoriteDialogPage.userSelectLastOption();
        favoriteDialogPage.save();
        expect(favoriteDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class FavoriteComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-favorite div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class FavoriteDialogPage {
    modalTitle = element(by.css('h4#myFavoriteLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    requestidInput = element(by.css('input#field_requestid'));
    preorderidInput = element(by.css('input#field_preorderid'));
    userSelect = element(by.css('select#field_user'));

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
