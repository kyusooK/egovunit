
import { useEffect, useState } from 'react'

import { Link, useLocation, useNavigate } from 'react-router-dom'

import Dialog from '@mui/material/Dialog';
import DialogTitle from '@mui/material/DialogTitle';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import TextField from '@mui/material/TextField';

import axios from 'axios';

import * as EgovNet from 'api/egovFetch'
import { NOTICE_BBS_ID } from 'config'
import CODE from 'constants/code'
import URL from 'constants/url'

import EgovAttachFile from 'components/EgovAttachFile'
import { default as EgovLeftNav } from 'components/leftmenu/EgovLeftNavInform'

function EgovNoticeDetail(props) {
    console.group("EgovNoticeDetail");
    console.log("EgovNoticeDetail [props] : ", props);

    const navigate = useNavigate();
    const location = useLocation();
    console.log("EgovNoticeDetail [location] : ", location);

    // const bbsId = location.state.bbsId || NOTICE_BBS_ID;
    const deliveryId = location.state.deliveryId;
    const searchCondition = location.state.searchCondition;

    const [createDeliveryopen, setCreateDeliveryOpen] = useState(false);
    const [completeDeliveryopen, setCompleteDeliveryOpen] = useState(false);
    const condition = true; 

    const [entity, setEntity] = useState("");

    const [masterBoard, setMasterBoard] = useState({});
    const [user, setUser] = useState({});
    const [boardDetail, setBoardDetail] = useState({});
    const [boardAttachFiles, setBoardAttachFiles] = useState();

    const retrieveDetail = () => {
        const retrieveDetailURL = `/deliveries/${deliveryId}`;
        const requestOptions = {
            method: "GET",
            headers: {
                'Content-type': 'application/json'
            }
        }
        EgovNet.requestFetch(retrieveDetailURL,
            requestOptions,
            function (resp) {
                setBoardDetail(resp);
            }
        );
    }
    useEffect(function () {
        retrieveDetail();
    // eslint-disable-next-line react-hooks/exhaustive-deps
    }, []);

    fetchDelivery(deliveryId){
        axios.get(`/deliveries/${deliveryId}`)
        .then(response => {
            setBoardDetail(response.data);
        })
    }

    function deleteList(){
        axios.delete(`/deliveries/${deliveryId}`)
        navigate('/delivery/deliveries');
    }

    function createDelivery(){

        axios.put(`/deliveries/${deliveryId}/createdelivery`, {deliveryId: entity }) 
        .then(response => {
            const resp = response.data
            if(!){
                navigate({pathname: URL.ERROR}, {state: {msg: resp.resultMessage}});
            }else{
                setCreateDeliveryOpen(false);
                fetchDelivery(deliveryId);
            }
    }
    function completeDelivery(){

        axios.put(`/deliveries/${deliveryId}/completedelivery`, {deliveryId: entity }) 
        .then(response => {
            const resp = response.data
            if(!){
                navigate({pathname: URL.ERROR}, {state: {msg: resp.resultMessage}});
            }else{
                setCompleteDeliveryOpen(false);
                fetchDelivery(deliveryId);
            }
    }

    return (
        <div className="container">
            <div className="c_wrap">
                {/* <!-- Location --> */}
                <div className="location">
                    <ul>
                        <li><Link to={URL.MAIN} className="home">Home</Link></li>
                        <li><Link to="/delivery/deliveries">Delivery</Link></li>
                        <li>{masterBoard && masterBoard.bbsNm}</li>
                    </ul>
                </div>
                {/* <!--// Location --> */}

                <div className="layout">
                    {/* <!-- Navigation --> */}
                    <EgovLeftNav></EgovLeftNav>
                    {/* <!--// Navigation --> */}

                    <div className="contents NOTICE_VIEW" id="contents">
                        {/* <!-- 본문 --> */}

                        <div className="top_tit">
                            <h1 className="tit_1">Delivery</h1>
                        </div>

                        {/* <!-- 게시판 상세보기 --> */}
                        <div className="board_view">
                            <div className="board_view_top">
                                <div className="tit">{deliveryId}</div>
                                <div className="info">
                                    <dl>
                                        <dt>DeliveryId</dt>
                                        <dd>{deliveryId}</dd>
                                    </dl>
                                    <dl>
                                        <dt>OrderId</dt>
                                        <dd>{boardDetail && boardDetail.orderId }</dd>
                                    </dl>
                                    <dl>
                                        <dt>ProductId</dt>
                                        <dd>{boardDetail && boardDetail.productId }</dd>
                                    </dl>
                                    <dl>
                                        <dt>ProductName</dt>
                                        <dd>{boardDetail && boardDetail.productName }</dd>
                                    </dl>
                                    <dl>
                                        <dt>Qty</dt>
                                        <dd>{boardDetail && boardDetail.qty }</dd>
                                    </dl>
                                </div>
                            </div>
                            <div className="board_btn_area">
                                <div style={{ display: "flex", flexDirection: "row"}}>
                                    <div style={{marginTop: "5px"}}>
                                        <button className="btn btn_blue_h46 w_100"
                                         onClick={() => {
                                            if (condition) {  
                                            setCreateDeliveryOpen(true);
                                            }
                                        }}>
                                            CreateDelivery
                                        </button>
                                        <button className="btn btn_blue_h46 w_100"
                                         onClick={() => {
                                            if (condition) {  
                                            setCompleteDeliveryOpen(true);
                                            }
                                        }}>
                                            CompleteDelivery
                                        </button>
                                    </div>
                                </div>
                                <div className="right_col btn1" style={{marginTop: "5px"}}>
                                    <Link to="/delivery/deliveries"
                                        className="btn btn_blue_h46 w_100">목록</Link>
                                </div>
                                <div className="right_col btn1" style={{marginTop: "5px", marginRight: "9%"}}>
                                    <button
                                        onClick={deleteList}
                                        className="btn btn_blue_h46 w_100">삭제
                                    </button>
                                </div>
                            </div>
                        </div>
                        {/* <!-- 게시판 상세보기 --> */}
                        <div>
                            <Dialog open={createDeliveryopen} onClose={() => setCreateDeliveryOpen(false)}>
                                <DialogTitle>CreateDelivery</DialogTitle>
                                <DialogContent>
                                    <TextField 
                                        autoFocus
                                        margin="dense"
                                        id=""
                                        label=""
                                        type="text"
                                        fullWidth
                                        value={entity}
                                        onChange={(e) => setEntity(e.target.value)}
                                    />
                                </DialogContent>
                                <DialogActions>
                                    <button onClick={() => setCreateDeliveryOpen(false)} className="btn btn_blue_h46 w_100">
                                        취소
                                    </button>
                                    <button onClick={createDelivery} className="btn btn_blue_h46 w_100">
                                    CreateDelivery
                                    </button>
                                </DialogActions>
                            </Dialog>
                        </div>
                        <div>
                            <Dialog open={completeDeliveryopen} onClose={() => setCompleteDeliveryOpen(false)}>
                                <DialogTitle>CompleteDelivery</DialogTitle>
                                <DialogContent>
                                    <TextField 
                                        autoFocus
                                        margin="dense"
                                        id=""
                                        label=""
                                        type="text"
                                        fullWidth
                                        value={entity}
                                        onChange={(e) => setEntity(e.target.value)}
                                    />
                                </DialogContent>
                                <DialogActions>
                                    <button onClick={() => setCompleteDeliveryOpen(false)} className="btn btn_blue_h46 w_100">
                                        취소
                                    </button>
                                    <button onClick={completeDelivery} className="btn btn_blue_h46 w_100">
                                    CompleteDelivery
                                    </button>
                                </DialogActions>
                            </Dialog>
                        </div>
                        
                        {/* <!--// 본문 --> */}
                    </div>
                </div>
            </div>
        </div>
    );
}


export default EgovNoticeDetail;
