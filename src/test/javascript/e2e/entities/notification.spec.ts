import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('Notification e2e test', () => {

    let navBarPage: NavBarPage;
    let notificationDialogPage: NotificationDialogPage;
    let notificationComponentsPage: NotificationComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Notifications', () => {
        navBarPage.goToEntity('notification');
        notificationComponentsPage = new NotificationComponentsPage();
        expect(notificationComponentsPage.getTitle())
            .toMatch(/travelApp.notification.home.title/);

    });

    it('should load create Notification dialog', () => {
        notificationComponentsPage.clickOnCreateButton();
        notificationDialogPage = new NotificationDialogPage();
        expect(notificationDialogPage.getModalTitle())
            .toMatch(/travelApp.notification.home.createOrEditLabel/);
        notificationDialogPage.close();
    });

    it('should create and save Notifications', () => {
        notificationComponentsPage.clickOnCreateButton();
        notificationDialogPage.setTextInput('text');
        expect(notificationDialogPage.getTextInput()).toMatch('text');
        notificationDialogPage.setNotifDateInput(12310020012301);
        expect(notificationDialogPage.getNotifDateInput()).toMatch('2001-12-31T02:30');
        notificationDialogPage.userSelectLastOption();
        notificationDialogPage.requestSelectLastOption();
        notificationDialogPage.save();
        expect(notificationDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class NotificationComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-notification div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class NotificationDialogPage {
    modalTitle = element(by.css('h4#myNotificationLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    textInput = element(by.css('textarea#field_text'));
    notifDateInput = element(by.css('input#field_notifDate'));
    userSelect = element(by.css('select#field_user'));
    requestSelect = element(by.css('select#field_request'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setTextInput = function(text) {
        this.textInput.sendKeys(text);
    };

    getTextInput = function() {
        return this.textInput.getAttribute('value');
    };

    setNotifDateInput = function(notifDate) {
        this.notifDateInput.sendKeys(notifDate);
    };

    getNotifDateInput = function() {
        return this.notifDateInput.getAttribute('value');
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
