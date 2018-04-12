import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('Messages e2e test', () => {

    let navBarPage: NavBarPage;
    let messagesDialogPage: MessagesDialogPage;
    let messagesComponentsPage: MessagesComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Messages', () => {
        navBarPage.goToEntity('messages');
        messagesComponentsPage = new MessagesComponentsPage();
        expect(messagesComponentsPage.getTitle())
            .toMatch(/travelApp.messages.home.title/);

    });

    it('should load create Messages dialog', () => {
        messagesComponentsPage.clickOnCreateButton();
        messagesDialogPage = new MessagesDialogPage();
        expect(messagesDialogPage.getModalTitle())
            .toMatch(/travelApp.messages.home.createOrEditLabel/);
        messagesDialogPage.close();
    });

    it('should create and save Messages', () => {
        messagesComponentsPage.clickOnCreateButton();
        messagesDialogPage.setTextInput('text');
        expect(messagesDialogPage.getTextInput()).toMatch('text');
        messagesDialogPage.setPostDateInput(12310020012301);
        expect(messagesDialogPage.getPostDateInput()).toMatch('2001-12-31T02:30');
        messagesDialogPage.userSelectLastOption();
        messagesDialogPage.inboxSelectLastOption();
        messagesDialogPage.save();
        expect(messagesDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class MessagesComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-messages div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class MessagesDialogPage {
    modalTitle = element(by.css('h4#myMessagesLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    textInput = element(by.css('textarea#field_text'));
    postDateInput = element(by.css('input#field_postDate'));
    userSelect = element(by.css('select#field_user'));
    inboxSelect = element(by.css('select#field_inbox'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setTextInput = function(text) {
        this.textInput.sendKeys(text);
    };

    getTextInput = function() {
        return this.textInput.getAttribute('value');
    };

    setPostDateInput = function(postDate) {
        this.postDateInput.sendKeys(postDate);
    };

    getPostDateInput = function() {
        return this.postDateInput.getAttribute('value');
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

    inboxSelectLastOption = function() {
        this.inboxSelect.all(by.tagName('option')).last().click();
    };

    inboxSelectOption = function(option) {
        this.inboxSelect.sendKeys(option);
    };

    getInboxSelect = function() {
        return this.inboxSelect;
    };

    getInboxSelectedOption = function() {
        return this.inboxSelect.element(by.css('option:checked')).getText();
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
