import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('Inbox e2e test', () => {

    let navBarPage: NavBarPage;
    let inboxDialogPage: InboxDialogPage;
    let inboxComponentsPage: InboxComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Inboxes', () => {
        navBarPage.goToEntity('inbox');
        inboxComponentsPage = new InboxComponentsPage();
        expect(inboxComponentsPage.getTitle())
            .toMatch(/travelApp.inbox.home.title/);

    });

    it('should load create Inbox dialog', () => {
        inboxComponentsPage.clickOnCreateButton();
        inboxDialogPage = new InboxDialogPage();
        expect(inboxDialogPage.getModalTitle())
            .toMatch(/travelApp.inbox.home.createOrEditLabel/);
        inboxDialogPage.close();
    });

    it('should create and save Inboxes', () => {
        inboxComponentsPage.clickOnCreateButton();
        inboxDialogPage.setSubjectInput('subject');
        expect(inboxDialogPage.getSubjectInput()).toMatch('subject');
        inboxDialogPage.setPostDateInput(12310020012301);
        expect(inboxDialogPage.getPostDateInput()).toMatch('2001-12-31T02:30');
        inboxDialogPage.senderSelectLastOption();
        inboxDialogPage.receiverSelectLastOption();
        inboxDialogPage.save();
        expect(inboxDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class InboxComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-inbox div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class InboxDialogPage {
    modalTitle = element(by.css('h4#myInboxLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    subjectInput = element(by.css('textarea#field_subject'));
    postDateInput = element(by.css('input#field_postDate'));
    senderSelect = element(by.css('select#field_sender'));
    receiverSelect = element(by.css('select#field_receiver'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setSubjectInput = function(subject) {
        this.subjectInput.sendKeys(subject);
    };

    getSubjectInput = function() {
        return this.subjectInput.getAttribute('value');
    };

    setPostDateInput = function(postDate) {
        this.postDateInput.sendKeys(postDate);
    };

    getPostDateInput = function() {
        return this.postDateInput.getAttribute('value');
    };

    senderSelectLastOption = function() {
        this.senderSelect.all(by.tagName('option')).last().click();
    };

    senderSelectOption = function(option) {
        this.senderSelect.sendKeys(option);
    };

    getSenderSelect = function() {
        return this.senderSelect;
    };

    getSenderSelectedOption = function() {
        return this.senderSelect.element(by.css('option:checked')).getText();
    };

    receiverSelectLastOption = function() {
        this.receiverSelect.all(by.tagName('option')).last().click();
    };

    receiverSelectOption = function(option) {
        this.receiverSelect.sendKeys(option);
    };

    getReceiverSelect = function() {
        return this.receiverSelect;
    };

    getReceiverSelectedOption = function() {
        return this.receiverSelect.element(by.css('option:checked')).getText();
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
