/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { TravelTestModule } from '../../../test.module';
import { PreOrderComponent } from '../../../../../../main/webapp/app/entities/pre-order/pre-order.component';
import { PreOrderService } from '../../../../../../main/webapp/app/entities/pre-order/pre-order.service';
import { PreOrder } from '../../../../../../main/webapp/app/entities/pre-order/pre-order.model';

describe('Component Tests', () => {

    describe('PreOrder Management Component', () => {
        let comp: PreOrderComponent;
        let fixture: ComponentFixture<PreOrderComponent>;
        let service: PreOrderService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [TravelTestModule],
                declarations: [PreOrderComponent],
                providers: [
                    PreOrderService
                ]
            })
            .overrideTemplate(PreOrderComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PreOrderComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PreOrderService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new PreOrder(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.preOrders[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
