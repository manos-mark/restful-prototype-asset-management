import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ProductPictureComponent } from './product-picture.component';

describe('ProductPictureComponent', () => {
  let component: ProductPictureComponent;
  let fixture: ComponentFixture<ProductPictureComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ProductPictureComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ProductPictureComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
