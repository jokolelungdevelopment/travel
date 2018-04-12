/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { TravelTestModule } from '../../../test.module';
import { InboxDetailComponent } from '../../../../../../main/webapp/app/entities/inbox/inbox-detail.component';
import { InboxService } from '../../../../../../main/webapp/app/entities/inbox/inbox.service';
import { Inbox } from '../../../../../../main/webapp/app/entities/inbox/inbox.model';

describe('Component Tests', () => {

    describe('Inbox Management Detail Component', () => {
        let comp: InboxDetailComponent;
        let fixture: ComponentFixture<InboxDetailComponent>;
        let service: InboxService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [TravelTestModule],
                declarations: [InboxDetailComponent],
                providers: [
                    InboxService
                ]
            })
            .overrideTemplate(InboxDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(InboxDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(InboxService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Inbox(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.inbox).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
