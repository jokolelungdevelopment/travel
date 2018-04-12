import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('Product e2e test', () => {

    let navBarPage: NavBarPage;
    let productDialogPage: ProductDialogPage;
    let productComponentsPage: ProductComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Products', () => {
        navBarPage.goToEntity('product');
        productComponentsPage = new ProductComponentsPage();
        expect(productComponentsPage.getTitle())
            .toMatch(/travelApp.product.home.title/);

    });

    it('should load create Product dialog', () => {
        productComponentsPage.clickOnCreateButton();
        productDialogPage = new ProductDialogPage();
        expect(productDialogPage.getModalTitle())
            .toMatch(/travelApp.product.home.createOrEditLabel/);
        productDialogPage.close();
    });

    it('should create and save Products', () => {
        productComponentsPage.clickOnCreateButton();
        productDialogPage.setProductnameInput('productname');
        expect(productDialogPage.getProductnameInput()).toMatch('productname');
        productDialogPage.setDescriptionInput('description');
        expect(productDialogPage.getDescriptionInput()).toMatch('description');
        productDialogPage.setBrandInput('brand');
        expect(productDialogPage.getBrandInput()).toMatch('brand');
        productDialogPage.setUrlInput('url');
        expect(productDialogPage.getUrlInput()).toMatch('url');
        productDialogPage.setQuantityInput('quantity');
        expect(productDialogPage.getQuantityInput()).toMatch('quantity');
        productDialogPage.getSpesialtreatmentInput().isSelected().then((selected) => {
            if (selected) {
                productDialogPage.getSpesialtreatmentInput().click();
                expect(productDialogPage.getSpesialtreatmentInput().isSelected()).toBeFalsy();
            } else {
                productDialogPage.getSpesialtreatmentInput().click();
                expect(productDialogPage.getSpesialtreatmentInput().isSelected()).toBeTruthy();
            }
        });
        productDialogPage.getFragileInput().isSelected().then((selected) => {
            if (selected) {
                productDialogPage.getFragileInput().click();
                expect(productDialogPage.getFragileInput().isSelected()).toBeFalsy();
            } else {
                productDialogPage.getFragileInput().click();
                expect(productDialogPage.getFragileInput().isSelected()).toBeTruthy();
            }
        });
        productDialogPage.setProductweightInput('5');
        expect(productDialogPage.getProductweightInput()).toMatch('5');
        productDialogPage.productsizeSelectLastOption();
        productDialogPage.setProductpriceInput('5');
        expect(productDialogPage.getProductpriceInput()).toMatch('5');
        productDialogPage.setPruducttipInput('5');
        expect(productDialogPage.getPruducttipInput()).toMatch('5');
        productDialogPage.setAdditionalchargeInput('5');
        expect(productDialogPage.getAdditionalchargeInput()).toMatch('5');
        productDialogPage.setTotalInput('5');
        expect(productDialogPage.getTotalInput()).toMatch('5');
        productDialogPage.userSelectLastOption();
        productDialogPage.categorySelectLastOption();
        productDialogPage.citySelectLastOption();
        productDialogPage.countrySelectLastOption();
        productDialogPage.currencySelectLastOption();
        productDialogPage.save();
        expect(productDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class ProductComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-product div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class ProductDialogPage {
    modalTitle = element(by.css('h4#myProductLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    productnameInput = element(by.css('input#field_productname'));
    descriptionInput = element(by.css('textarea#field_description'));
    brandInput = element(by.css('input#field_brand'));
    urlInput = element(by.css('input#field_url'));
    quantityInput = element(by.css('input#field_quantity'));
    spesialtreatmentInput = element(by.css('input#field_spesialtreatment'));
    fragileInput = element(by.css('input#field_fragile'));
    productweightInput = element(by.css('input#field_productweight'));
    productsizeSelect = element(by.css('select#field_productsize'));
    productpriceInput = element(by.css('input#field_productprice'));
    pruducttipInput = element(by.css('input#field_pruducttip'));
    additionalchargeInput = element(by.css('input#field_additionalcharge'));
    totalInput = element(by.css('input#field_total'));
    userSelect = element(by.css('select#field_user'));
    categorySelect = element(by.css('select#field_category'));
    citySelect = element(by.css('select#field_city'));
    countrySelect = element(by.css('select#field_country'));
    currencySelect = element(by.css('select#field_currency'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setProductnameInput = function(productname) {
        this.productnameInput.sendKeys(productname);
    };

    getProductnameInput = function() {
        return this.productnameInput.getAttribute('value');
    };

    setDescriptionInput = function(description) {
        this.descriptionInput.sendKeys(description);
    };

    getDescriptionInput = function() {
        return this.descriptionInput.getAttribute('value');
    };

    setBrandInput = function(brand) {
        this.brandInput.sendKeys(brand);
    };

    getBrandInput = function() {
        return this.brandInput.getAttribute('value');
    };

    setUrlInput = function(url) {
        this.urlInput.sendKeys(url);
    };

    getUrlInput = function() {
        return this.urlInput.getAttribute('value');
    };

    setQuantityInput = function(quantity) {
        this.quantityInput.sendKeys(quantity);
    };

    getQuantityInput = function() {
        return this.quantityInput.getAttribute('value');
    };

    getSpesialtreatmentInput = function() {
        return this.spesialtreatmentInput;
    };
    getFragileInput = function() {
        return this.fragileInput;
    };
    setProductweightInput = function(productweight) {
        this.productweightInput.sendKeys(productweight);
    };

    getProductweightInput = function() {
        return this.productweightInput.getAttribute('value');
    };

    setProductsizeSelect = function(productsize) {
        this.productsizeSelect.sendKeys(productsize);
    };

    getProductsizeSelect = function() {
        return this.productsizeSelect.element(by.css('option:checked')).getText();
    };

    productsizeSelectLastOption = function() {
        this.productsizeSelect.all(by.tagName('option')).last().click();
    };
    setProductpriceInput = function(productprice) {
        this.productpriceInput.sendKeys(productprice);
    };

    getProductpriceInput = function() {
        return this.productpriceInput.getAttribute('value');
    };

    setPruducttipInput = function(pruducttip) {
        this.pruducttipInput.sendKeys(pruducttip);
    };

    getPruducttipInput = function() {
        return this.pruducttipInput.getAttribute('value');
    };

    setAdditionalchargeInput = function(additionalcharge) {
        this.additionalchargeInput.sendKeys(additionalcharge);
    };

    getAdditionalchargeInput = function() {
        return this.additionalchargeInput.getAttribute('value');
    };

    setTotalInput = function(total) {
        this.totalInput.sendKeys(total);
    };

    getTotalInput = function() {
        return this.totalInput.getAttribute('value');
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

    categorySelectLastOption = function() {
        this.categorySelect.all(by.tagName('option')).last().click();
    };

    categorySelectOption = function(option) {
        this.categorySelect.sendKeys(option);
    };

    getCategorySelect = function() {
        return this.categorySelect;
    };

    getCategorySelectedOption = function() {
        return this.categorySelect.element(by.css('option:checked')).getText();
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

    currencySelectLastOption = function() {
        this.currencySelect.all(by.tagName('option')).last().click();
    };

    currencySelectOption = function(option) {
        this.currencySelect.sendKeys(option);
    };

    getCurrencySelect = function() {
        return this.currencySelect;
    };

    getCurrencySelectedOption = function() {
        return this.currencySelect.element(by.css('option:checked')).getText();
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
