import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { PreOrder } from './pre-order.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<PreOrder>;

@Injectable()
export class PreOrderService {

    private resourceUrl =  SERVER_API_URL + 'api/pre-orders';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(preOrder: PreOrder): Observable<EntityResponseType> {
        const copy = this.convert(preOrder);
        return this.http.post<PreOrder>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(preOrder: PreOrder): Observable<EntityResponseType> {
        const copy = this.convert(preOrder);
        return this.http.put<PreOrder>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<PreOrder>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<PreOrder[]>> {
        const options = createRequestOption(req);
        return this.http.get<PreOrder[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<PreOrder[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: PreOrder = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<PreOrder[]>): HttpResponse<PreOrder[]> {
        const jsonResponse: PreOrder[] = res.body;
        const body: PreOrder[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to PreOrder.
     */
    private convertItemFromServer(preOrder: PreOrder): PreOrder {
        const copy: PreOrder = Object.assign({}, preOrder);
        copy.orderDate = this.dateUtils
            .convertDateTimeFromServer(preOrder.orderDate);
        return copy;
    }

    /**
     * Convert a PreOrder to a JSON which can be sent to the server.
     */
    private convert(preOrder: PreOrder): PreOrder {
        const copy: PreOrder = Object.assign({}, preOrder);

        copy.orderDate = this.dateUtils.toDate(preOrder.orderDate);
        return copy;
    }
}
