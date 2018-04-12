/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { TravelTestModule } from '../../../test.module';
import { InboxComponent } from '../../../../../../main/webapp/app/entities/inbox/inbox.component';
import { InboxService } from '../../../../../../main/webapp/app/entities/inbox/inbox.service';
import { Inbox } from '../../../../../../main/webapp/app/entities/inbox/inbox.model';

describe('Component Tests', () => {

    describe('Inbox Management Component', () => {
        let comp: InboxComponent;
        let fixture: ComponentFixture<InboxComponent>;
        let service: InboxService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [TravelTestModule],
                declarations: [InboxComponent],
                providers: [
                    InboxService
                ]
            })
            .overrideTemplate(InboxComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(InboxComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(InboxService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Inbox(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.inboxes[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
