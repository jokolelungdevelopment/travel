import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('Discussion e2e test', () => {

    let navBarPage: NavBarPage;
    let discussionDialogPage: DiscussionDialogPage;
    let discussionComponentsPage: DiscussionComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Discussions', () => {
        navBarPage.goToEntity('discussion');
        discussionComponentsPage = new DiscussionComponentsPage();
        expect(discussionComponentsPage.getTitle())
            .toMatch(/travelApp.discussion.home.title/);

    });

    it('should load create Discussion dialog', () => {
        discussionComponentsPage.clickOnCreateButton();
        discussionDialogPage = new DiscussionDialogPage();
        expect(discussionDialogPage.getModalTitle())
            .toMatch(/travelApp.discussion.home.createOrEditLabel/);
        discussionDialogPage.close();
    });

    it('should create and save Discussions', () => {
        discussionComponentsPage.clickOnCreateButton();
        discussionDialogPage.setTextInput('text');
        expect(discussionDialogPage.getTextInput()).toMatch('text');
        discussionDialogPage.setPostDateInput(12310020012301);
        expect(discussionDialogPage.getPostDateInput()).toMatch('2001-12-31T02:30');
        discussionDialogPage.setRequestidInput('5');
        expect(discussionDialogPage.getRequestidInput()).toMatch('5');
        discussionDialogPage.setPreorderidInput('5');
        expect(discussionDialogPage.getPreorderidInput()).toMatch('5');
        discussionDialogPage.userSelectLastOption();
        discussionDialogPage.save();
        expect(discussionDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class DiscussionComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-discussion div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class DiscussionDialogPage {
    modalTitle = element(by.css('h4#myDiscussionLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    textInput = element(by.css('textarea#field_text'));
    postDateInput = element(by.css('input#field_postDate'));
    requestidInput = element(by.css('input#field_requestid'));
    preorderidInput = element(by.css('input#field_preorderid'));
    userSelect = element(by.css('select#field_user'));

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
