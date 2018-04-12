import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Offer } from './offer.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Offer>;

@Injectable()
export class OfferService {

    private resourceUrl =  SERVER_API_URL + 'api/offers';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(offer: Offer): Observable<EntityResponseType> {
        const copy = this.convert(offer);
        return this.http.post<Offer>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(offer: Offer): Observable<EntityResponseType> {
        const copy = this.convert(offer);
        return this.http.put<Offer>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Offer>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Offer[]>> {
        const options = createRequestOption(req);
        return this.http.get<Offer[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Offer[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Offer = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Offer[]>): HttpResponse<Offer[]> {
        const jsonResponse: Offer[] = res.body;
        const body: Offer[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Offer.
     */
    private convertItemFromServer(offer: Offer): Offer {
        const copy: Offer = Object.assign({}, offer);
        copy.offerDate = this.dateUtils
            .convertDateTimeFromServer(offer.offerDate);
        return copy;
    }

    /**
     * Convert a Offer to a JSON which can be sent to the server.
     */
    private convert(offer: Offer): Offer {
        const copy: Offer = Object.assign({}, offer);

        copy.offerDate = this.dateUtils.toDate(offer.offerDate);
        return copy;
    }
}
