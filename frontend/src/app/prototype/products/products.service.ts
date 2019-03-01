import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from "@angular/core";
import { Product } from './product.model';
import { ProductPicture } from './product-picture.model';
import { PageParams } from '../projects/page-params.model';
import { FilterParams } from '../projects/filter-params.model';
import { toHttpParams } from 'src/app/shared/http-params-converter';
import { Statuses } from '../status.enum';

@Injectable()
export class ProductsService {
    editMode = false;

    constructor(private httpClient: HttpClient) {}

    getProductsCountByStatusId(id: number) {
        return this.httpClient.post<number>('api/products/count', {
            statusId: id
        }, 
        {
            headers: new HttpHeaders().set('Content-Type', 'application/json'),
            observe: 'body'
        })
        
    }

    getProducts(pageParams: PageParams, filterParams: FilterParams) {

        const params = {
            field: pageParams.field,
            page: pageParams.page,
            pageSize: pageParams.pageSize,
            direction: pageParams.direction,
            fromDate: filterParams.fromDate,
            toDate: filterParams.toDate,
            statusId: filterParams.statusId,
            projectName: filterParams.projectName
        }
        return this.httpClient.get<Product[]>('api/products/',  
        {
            headers: new HttpHeaders().set('Content-Type', 'application/json'),
            observe: 'body',
            params: toHttpParams(params)
        })
    }

    updateProduct(product: Product) {
        return this.httpClient.put<any>('api/products/' + product.id,
        {
            productName: product.productName,
            serialNumber: product.serialNumber,
            description: product.description,
            quantity: product.quantity,
            statusId: Statuses.NEW,
            projectId: product.projectId
        },
        {
            headers: new HttpHeaders().set('Content-Type', 'application/json'),
            observe: 'response'
        })
    }

    addProduct(product: Product) {
        return this.httpClient.post<any>('api/products', 
            {
                productName: product.productName,
                serialNumber: product.serialNumber,
                description: product.description,
                quantity: product.quantity,
                statusId: product.status.id,
                projectId: product.projectId
            },
            {
                headers: new HttpHeaders().set('Content-Type', 'application/json'),
                observe: 'body',
            }
        )
    }

    deleteProduct(productId: number) {
        return this.httpClient.delete('api/products/' + productId,  
        {
            headers: new HttpHeaders().set('Content-Type', 'application/json'),
            observe: 'response'
        })
    }

    getProductById(productId: number) {
        return this.httpClient.get('api/products/' + productId,  
        {
            headers: new HttpHeaders().set('Content-Type', 'application/json'),
            observe: 'response'
        })
    }

    // addPicture(productId: number, picture: ProductPicture) {
    //     return this.httpClient.post<any>('api/products/' + productId + '/pictures',
    //         {
    //             productId: productId,
    //             picture: picture.file,
    //             thumb: picture.thumb,
    //             name: picture.file.name, 
    //             size: picture.file.size
    //         },
    //         {
    //             headers: new HttpHeaders().set('Content-Type', 'application/json'),
    //             observe: 'body',
    //         }
    //     )
    // }

    // getPicturesByProductId(productId: number) {
    //     return this.httpClient.get<URL[]>('api/products/' + productId + '/pictures',  
    //     {
    //         headers: new HttpHeaders().set('Content-Type', 'image/png, image/jpg, image/gif'),
    //         observe: 'body'
    //     })
    // }
}