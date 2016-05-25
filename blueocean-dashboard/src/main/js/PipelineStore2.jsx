import keymirror from 'keymirror';
import Immutable from 'immutable';
import { PendingResult } from './Result';

const { Record } = Immutable;

const State = new Record({
    pipelines: new PendingResult(),
});

export const ACTION_TYPES = keymirror({
    CLEAR_PIPELINES_DATA2: null,
    SET_PIPELINES_DATA2: null,
});

const actionHandlers = {
    [ACTION_TYPES.CLEAR_PIPELINES_DATA2](state) {
        return state.set('pipelines', new PendingResult());
    },
    [ACTION_TYPES.SET_PIPELINES_DATA2](state, { payload }): State {
        return state.set('pipelines', payload);
    },
};

export function reducer(state = new State(), action:Object):State {
    const { type } = action;
    if (type in actionHandlers) {
        return actionHandlers[type](state, action);
    }
    return state;
}

export default {
    testStore: reducer,
};