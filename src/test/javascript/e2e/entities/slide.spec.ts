import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('Slide e2e test', () => {

    let navBarPage: NavBarPage;
    let slideDialogPage: SlideDialogPage;
    let slideComponentsPage: SlideComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Slides', () => {
        navBarPage.goToEntity('slide');
        slideComponentsPage = new SlideComponentsPage();
        expect(slideComponentsPage.getTitle())
            .toMatch(/travelApp.slide.home.title/);

    });

    it('should load create Slide dialog', () => {
        slideComponentsPage.clickOnCreateButton();
        slideDialogPage = new SlideDialogPage();
        expect(slideDialogPage.getModalTitle())
            .toMatch(/travelApp.slide.home.createOrEditLabel/);
        slideDialogPage.close();
    });

    it('should create and save Slides', () => {
        slideComponentsPage.clickOnCreateButton();
        slideDialogPage.setImgurlInput('5');
        expect(slideDialogPage.getImgurlInput()).toMatch('5');
        slideDialogPage.setUrlInput('url');
        expect(slideDialogPage.getUrlInput()).toMatch('url');
        slideDialogPage.save();
        expect(slideDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class SlideComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-slide div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class SlideDialogPage {
    modalTitle = element(by.css('h4#mySlideLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    imgurlInput = element(by.css('input#field_imgurl'));
    urlInput = element(by.css('input#field_url'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setImgurlInput = function(imgurl) {
        this.imgurlInput.sendKeys(imgurl);
    };

    getImgurlInput = function() {
        return this.imgurlInput.getAttribute('value');
    };

    setUrlInput = function(url) {
        this.urlInput.sendKeys(url);
    };

    getUrlInput = function() {
        return this.urlInput.getAttribute('value');
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
