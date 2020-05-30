package util;

import view.frames.CentralFrame;

import javax.swing.*;
import java.util.List;
import java.util.ListIterator;

public class BackAndForward {


    private ListIterator<JLabel> listIterator;
    private boolean setListIterator = true;
    private boolean forward = false;
    private boolean back = true;
    private CentralFrame centralFrame;

    public BackAndForward(){

    }

    public void setSetListIterator(boolean setListIterator) {
        this.setListIterator = setListIterator;
    }

    public void hatzInSpateHatzInFata(List<JLabel> list, String action ){
        centralFrame = CentralFrame.getInstance();

        if(!list.isEmpty()){
            if(setListIterator){
                System.out.println(list);
                System.out.println(setListIterator);
                listIterator = list.listIterator(list.size()-1);
                setListIterator =false;
                back = false;
            }
            
            switch(action){

                case "back":
                    if(centralFrame.getLoginPage().getY() !=0 && list.get(listIterator.previousIndex()).getY() == 0) { // <- anti-spam button
                 //       System.out.println("back " + back);
                        if (!back) {
                            if (listIterator.hasPrevious()) {
                                listIterator.previous();
                                back = true;
                            }
                        }
                    }

                    if(!(listIterator.nextIndex() >= list.size())){

                        if(centralFrame.getLoginPage().getY() !=0 && list.get(listIterator.nextIndex()).getY() == 0){ // <- anti-spam button
                            if(listIterator.previousIndex() == 0){
                                centralFrame.moveTwoLabelsDown(listIterator.previous());
                                list.clear();
                                setListIterator =true;
                            }
                      //      System.out.println(" back index" + listIterator.previousIndex());
                            if(listIterator.hasPrevious()){
                                centralFrame.oneLabelUpOneLabelDown(listIterator.previous());
                                forward = true;
                            }
                        }
                    }
                    break;

                case "forward":

                    if(forward){
                        if(centralFrame.getLoginPage().getY() !=0 && list.get(listIterator.nextIndex()).getY() == 0) { //<- anti-spam button

                            if (listIterator.hasNext()) {
                                listIterator.next();
                                forward = false;
                            }
                        }
                    }

                    if(centralFrame.getLoginPage().getY() !=0 && list.get(listIterator.previousIndex()).getY() == 0){ // <- anti-spam button
              //          System.out.println("forward index: " + listIterator.nextIndex());
                        if(listIterator.hasNext()){
                            centralFrame.oneLabelUpOneLabelDown(listIterator.next());
                            back = false;
                        }
                    }
                    break;
            }
        }
    }
}
