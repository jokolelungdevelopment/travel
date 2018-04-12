import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('UserInfo e2e test', () => {

    let navBarPage: NavBarPage;
    let userInfoDialogPage: UserInfoDialogPage;
    let userInfoComponentsPage: UserInfoComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load UserInfos', () => {
        navBarPage.goToEntity('user-info');
        userInfoComponentsPage = new UserInfoComponentsPage();
        expect(userInfoComponentsPage.getTitle())
            .toMatch(/travelApp.userInfo.home.title/);

    });

    it('should load create UserInfo dialog', () => {
        userInfoComponentsPage.clickOnCreateButton();
        userInfoDialogPage = new UserInfoDialogPage();
        expect(userInfoDialogPage.getModalTitle())
            .toMatch(/travelApp.userInfo.home.createOrEditLabel/);
        userInfoDialogPage.close();
    });

    it('should create and save UserInfos', () => {
        userInfoComponentsPage.clickOnCreateButton();
        userInfoDialogPage.setFullnameInput('fullname');
        expect(userInfoDialogPage.getFullnameInput()).toMatch('fullname');
        userInfoDialogPage.setBirthdateInput('birthdate');
        expect(userInfoDialogPage.getBirthdateInput()).toMatch('birthdate');
        userInfoDialogPage.setPhoneNumberInput('phoneNumber');
        expect(userInfoDialogPage.getPhoneNumberInput()).toMatch('phoneNumber');
        userInfoDialogPage.statusSelectLastOption();
        userInfoDialogPage.setBalanceInput('5');
        expect(userInfoDialogPage.getBalanceInput()).toMatch('5');
        userInfoDialogPage.setImgurlInput('imgurl');
        expect(userInfoDialogPage.getImgurlInput()).toMatch('imgurl');
        userInfoDialogPage.setGmailTokenInput('gmailToken');
        expect(userInfoDialogPage.getGmailTokenInput()).toMatch('gmailToken');
        userInfoDialogPage.setFacebookTokenInput('facebookToken');
        expect(userInfoDialogPage.getFacebookTokenInput()).toMatch('facebookToken');
        userInfoDialogPage.userSelectLastOption();
        userInfoDialogPage.save();
        expect(userInfoDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class UserInfoComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-user-info div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class UserInfoDialogPage {
    modalTitle = element(by.css('h4#myUserInfoLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    fullnameInput = element(by.css('input#field_fullname'));
    birthdateInput = element(by.css('input#field_birthdate'));
    phoneNumberInput = element(by.css('input#field_phoneNumber'));
    statusSelect = element(by.css('select#field_status'));
    balanceInput = element(by.css('input#field_balance'));
    imgurlInput = element(by.css('input#field_imgurl'));
    gmailTokenInput = element(by.css('textarea#field_gmailToken'));
    facebookTokenInput = element(by.css('textarea#field_facebookToken'));
    userSelect = element(by.css('select#field_user'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setFullnameInput = function(fullname) {
        this.fullnameInput.sendKeys(fullname);
    };

    getFullnameInput = function() {
        return this.fullnameInput.getAttribute('value');
    };

    setBirthdateInput = function(birthdate) {
        this.birthdateInput.sendKeys(birthdate);
    };

    getBirthdateInput = function() {
        return this.birthdateInput.getAttribute('value');
    };

    setPhoneNumberInput = function(phoneNumber) {
        this.phoneNumberInput.sendKeys(phoneNumber);
    };

    getPhoneNumberInput = function() {
        return this.phoneNumberInput.getAttribute('value');
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
    setBalanceInput = function(balance) {
        this.balanceInput.sendKeys(balance);
    };

    getBalanceInput = function() {
        return this.balanceInput.getAttribute('value');
    };

    setImgurlInput = function(imgurl) {
        this.imgurlInput.sendKeys(imgurl);
    };

    getImgurlInput = function() {
        return this.imgurlInput.getAttribute('value');
    };

    setGmailTokenInput = function(gmailToken) {
        this.gmailTokenInput.sendKeys(gmailToken);
    };

    getGmailTokenInput = function() {
        return this.gmailTokenInput.getAttribute('value');
    };

    setFacebookTokenInput = function(facebookToken) {
        this.facebookTokenInput.sendKeys(facebookToken);
    };

    getFacebookTokenInput = function() {
        return this.facebookTokenInput.getAttribute('value');
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
