/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { TravelTestModule } from '../../../test.module';
import { PreOrderDetailComponent } from '../../../../../../main/webapp/app/entities/pre-order/pre-order-detail.component';
import { PreOrderService } from '../../../../../../main/webapp/app/entities/pre-order/pre-order.service';
import { PreOrder } from '../../../../../../main/webapp/app/entities/pre-order/pre-order.model';

describe('Component Tests', () => {

    describe('PreOrder Management Detail Component', () => {
        let comp: PreOrderDetailComponent;
        let fixture: ComponentFixture<PreOrderDetailComponent>;
        let service: PreOrderService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [TravelTestModule],
                declarations: [PreOrderDetailComponent],
                providers: [
                    PreOrderService
                ]
            })
            .overrideTemplate(PreOrderDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PreOrderDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PreOrderService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new PreOrder(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.preOrder).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
