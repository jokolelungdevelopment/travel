/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { TravelTestModule } from '../../../test.module';
import { PreOrderDialogComponent } from '../../../../../../main/webapp/app/entities/pre-order/pre-order-dialog.component';
import { PreOrderService } from '../../../../../../main/webapp/app/entities/pre-order/pre-order.service';
import { PreOrder } from '../../../../../../main/webapp/app/entities/pre-order/pre-order.model';
import { UserService } from '../../../../../../main/webapp/app/shared';
import { ProductService } from '../../../../../../main/webapp/app/entities/product';
import { TripService } from '../../../../../../main/webapp/app/entities/trip';

describe('Component Tests', () => {

    describe('PreOrder Management Dialog Component', () => {
        let comp: PreOrderDialogComponent;
        let fixture: ComponentFixture<PreOrderDialogComponent>;
        let service: PreOrderService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [TravelTestModule],
                declarations: [PreOrderDialogComponent],
                providers: [
                    UserService,
                    ProductService,
                    TripService,
                    PreOrderService
                ]
            })
            .overrideTemplate(PreOrderDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PreOrderDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PreOrderService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new PreOrder(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.preOrder = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'preOrderListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new PreOrder();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.preOrder = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'preOrderListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
