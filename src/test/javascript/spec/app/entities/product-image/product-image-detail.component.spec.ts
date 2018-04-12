/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { TravelTestModule } from '../../../test.module';
import { ProductImageDetailComponent } from '../../../../../../main/webapp/app/entities/product-image/product-image-detail.component';
import { ProductImageService } from '../../../../../../main/webapp/app/entities/product-image/product-image.service';
import { ProductImage } from '../../../../../../main/webapp/app/entities/product-image/product-image.model';

describe('Component Tests', () => {

    describe('ProductImage Management Detail Component', () => {
        let comp: ProductImageDetailComponent;
        let fixture: ComponentFixture<ProductImageDetailComponent>;
        let service: ProductImageService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [TravelTestModule],
                declarations: [ProductImageDetailComponent],
                providers: [
                    ProductImageService
                ]
            })
            .overrideTemplate(ProductImageDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ProductImageDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProductImageService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new ProductImage(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.productImage).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
