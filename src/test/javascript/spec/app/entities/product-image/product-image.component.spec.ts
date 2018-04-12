/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { TravelTestModule } from '../../../test.module';
import { ProductImageComponent } from '../../../../../../main/webapp/app/entities/product-image/product-image.component';
import { ProductImageService } from '../../../../../../main/webapp/app/entities/product-image/product-image.service';
import { ProductImage } from '../../../../../../main/webapp/app/entities/product-image/product-image.model';

describe('Component Tests', () => {

    describe('ProductImage Management Component', () => {
        let comp: ProductImageComponent;
        let fixture: ComponentFixture<ProductImageComponent>;
        let service: ProductImageService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [TravelTestModule],
                declarations: [ProductImageComponent],
                providers: [
                    ProductImageService
                ]
            })
            .overrideTemplate(ProductImageComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ProductImageComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProductImageService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new ProductImage(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.productImages[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
