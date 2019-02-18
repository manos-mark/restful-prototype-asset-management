import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { WindowPopComponent } from './window-pop.component';

describe('WindowPopComponent', () => {
  let component: WindowPopComponent;
  let fixture: ComponentFixture<WindowPopComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ WindowPopComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(WindowPopComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
