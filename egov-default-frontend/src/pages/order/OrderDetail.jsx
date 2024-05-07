
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
    const orderId = location.state.orderId;
    const searchCondition = location.state.searchCondition;

    const [acceptOrderopen, setAcceptOrderOpen] = useState(false);
    const [rejectOrderopen, setRejectOrderOpen] = useState(false);
    const condition = true; 

    const [entity, setEntity] = useState("");

    const [masterBoard, setMasterBoard] = useState({});
    const [user, setUser] = useState({});
    const [boardDetail, setBoardDetail] = useState({});
    const [boardAttachFiles, setBoardAttachFiles] = useState();

    const retrieveDetail = () => {
        const retrieveDetailURL = `/orders/${orderId}`;
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

    fetchOrder(orderId){
        axios.get(`/orders/${orderId}`)
        .then(response => {
            setBoardDetail(response.data);
        })
    }

    function deleteList(){
        axios.delete(`/orders/${orderId}`)
        navigate('/order/orders');
    }

    function acceptOrder(){

        axios.put(`/orders/${orderId}/acceptorder`, {orderId: entity }) 
        .then(response => {
            const resp = response.data
            if(!){
                navigate({pathname: URL.ERROR}, {state: {msg: resp.resultMessage}});
            }else{
                setAcceptOrderOpen(false);
                fetchOrder(orderId);
            }
    }
    function rejectOrder(){

        axios.put(`/orders/${orderId}/rejectorder`, {orderId: entity }) 
        .then(response => {
            const resp = response.data
            if(!){
                navigate({pathname: URL.ERROR}, {state: {msg: resp.resultMessage}});
            }else{
                setRejectOrderOpen(false);
                fetchOrder(orderId);
            }
    }

    return (
        <div className="container">
            <div className="c_wrap">
                {/* <!-- Location --> */}
                <div className="location">
                    <ul>
                        <li><Link to={URL.MAIN} className="home">Home</Link></li>
                        <li><Link to="/order/orders">Order</Link></li>
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
                            <h1 className="tit_1">Order</h1>
                        </div>

                        {/* <!-- 게시판 상세보기 --> */}
                        <div className="board_view">
                            <div className="board_view_top">
                                <div className="tit">{orderId}</div>
                                <div className="info">
                                    <dl>
                                        <dt>OrderId</dt>
                                        <dd>{orderId}</dd>
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
                                            setAcceptOrderOpen(true);
                                            }
                                        }}>
                                            AcceptOrder
                                        </button>
                                        <button className="btn btn_blue_h46 w_100"
                                         onClick={() => {
                                            if (condition) {  
                                            setRejectOrderOpen(true);
                                            }
                                        }}>
                                            RejectOrder
                                        </button>
                                    </div>
                                </div>
                                <div className="right_col btn1" style={{marginTop: "5px"}}>
                                    <Link to="/order/orders"
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
                            <Dialog open={acceptOrderopen} onClose={() => setAcceptOrderOpen(false)}>
                                <DialogTitle>AcceptOrder</DialogTitle>
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
                                    <button onClick={() => setAcceptOrderOpen(false)} className="btn btn_blue_h46 w_100">
                                        취소
                                    </button>
                                    <button onClick={acceptOrder} className="btn btn_blue_h46 w_100">
                                    AcceptOrder
                                    </button>
                                </DialogActions>
                            </Dialog>
                        </div>
                        <div>
                            <Dialog open={rejectOrderopen} onClose={() => setRejectOrderOpen(false)}>
                                <DialogTitle>RejectOrder</DialogTitle>
                                <DialogContent>
                                    <TextField 
                                        autoFocus
                                        margin="dense"
                                        id="orderId"
                                        label="OrderId"
                                        type="text"
                                        fullWidth
                                        value={entity}
                                        onChange={(e) => setEntity(e.target.value)}
                                    />
                                </DialogContent>
                                <DialogActions>
                                    <button onClick={() => setRejectOrderOpen(false)} className="btn btn_blue_h46 w_100">
                                        취소
                                    </button>
                                    <button onClick={rejectOrder} className="btn btn_blue_h46 w_100">
                                    RejectOrder
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
