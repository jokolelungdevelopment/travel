/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { TravelTestModule } from '../../../test.module';
import { DiscussionDetailComponent } from '../../../../../../main/webapp/app/entities/discussion/discussion-detail.component';
import { DiscussionService } from '../../../../../../main/webapp/app/entities/discussion/discussion.service';
import { Discussion } from '../../../../../../main/webapp/app/entities/discussion/discussion.model';

describe('Component Tests', () => {

    describe('Discussion Management Detail Component', () => {
        let comp: DiscussionDetailComponent;
        let fixture: ComponentFixture<DiscussionDetailComponent>;
        let service: DiscussionService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [TravelTestModule],
                declarations: [DiscussionDetailComponent],
                providers: [
                    DiscussionService
                ]
            })
            .overrideTemplate(DiscussionDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DiscussionDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DiscussionService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Discussion(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.discussion).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
