import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('ProductImage e2e test', () => {

    let navBarPage: NavBarPage;
    let productImageDialogPage: ProductImageDialogPage;
    let productImageComponentsPage: ProductImageComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load ProductImages', () => {
        navBarPage.goToEntity('product-image');
        productImageComponentsPage = new ProductImageComponentsPage();
        expect(productImageComponentsPage.getTitle())
            .toMatch(/travelApp.productImage.home.title/);

    });

    it('should load create ProductImage dialog', () => {
        productImageComponentsPage.clickOnCreateButton();
        productImageDialogPage = new ProductImageDialogPage();
        expect(productImageDialogPage.getModalTitle())
            .toMatch(/travelApp.productImage.home.createOrEditLabel/);
        productImageDialogPage.close();
    });

    it('should create and save ProductImages', () => {
        productImageComponentsPage.clickOnCreateButton();
        productImageDialogPage.setImgUrlInput('imgUrl');
        expect(productImageDialogPage.getImgUrlInput()).toMatch('imgUrl');
        productImageDialogPage.productSelectLastOption();
        productImageDialogPage.save();
        expect(productImageDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class ProductImageComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-product-image div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class ProductImageDialogPage {
    modalTitle = element(by.css('h4#myProductImageLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    imgUrlInput = element(by.css('input#field_imgUrl'));
    productSelect = element(by.css('select#field_product'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setImgUrlInput = function(imgUrl) {
        this.imgUrlInput.sendKeys(imgUrl);
    };

    getImgUrlInput = function() {
        return this.imgUrlInput.getAttribute('value');
    };

    productSelectLastOption = function() {
        this.productSelect.all(by.tagName('option')).last().click();
    };

    productSelectOption = function(option) {
        this.productSelect.sendKeys(option);
    };

    getProductSelect = function() {
        return this.productSelect;
    };

    getProductSelectedOption = function() {
        return this.productSelect.element(by.css('option:checked')).getText();
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
