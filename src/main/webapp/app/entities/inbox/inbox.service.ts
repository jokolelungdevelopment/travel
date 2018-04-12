import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Inbox } from './inbox.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Inbox>;

@Injectable()
export class InboxService {

    private resourceUrl =  SERVER_API_URL + 'api/inboxes';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(inbox: Inbox): Observable<EntityResponseType> {
        const copy = this.convert(inbox);
        return this.http.post<Inbox>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(inbox: Inbox): Observable<EntityResponseType> {
        const copy = this.convert(inbox);
        return this.http.put<Inbox>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Inbox>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Inbox[]>> {
        const options = createRequestOption(req);
        return this.http.get<Inbox[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Inbox[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Inbox = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Inbox[]>): HttpResponse<Inbox[]> {
        const jsonResponse: Inbox[] = res.body;
        const body: Inbox[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Inbox.
     */
    private convertItemFromServer(inbox: Inbox): Inbox {
        const copy: Inbox = Object.assign({}, inbox);
        copy.postDate = this.dateUtils
            .convertDateTimeFromServer(inbox.postDate);
        return copy;
    }

    /**
     * Convert a Inbox to a JSON which can be sent to the server.
     */
    private convert(inbox: Inbox): Inbox {
        const copy: Inbox = Object.assign({}, inbox);

        copy.postDate = this.dateUtils.toDate(inbox.postDate);
        return copy;
    }
}
